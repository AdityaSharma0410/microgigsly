package com.proposalservice.proposalservice.service.impl;

import com.proposalservice.proposalservice.client.TaskClient;
import com.proposalservice.proposalservice.client.UserClient;
import com.proposalservice.proposalservice.dto.*;
import com.proposalservice.proposalservice.exception.BadRequestException;
import com.proposalservice.proposalservice.exception.ConflictException;
import com.proposalservice.proposalservice.exception.ProposalNotFoundException;
import com.proposalservice.proposalservice.exception.ResourceNotFoundException;
import com.proposalservice.proposalservice.mapper.ProposalMapper;
import com.proposalservice.proposalservice.model.Proposal;
import com.proposalservice.proposalservice.model.ProposalStatus;
import com.proposalservice.proposalservice.repository.ProposalRepository;
import com.proposalservice.proposalservice.security.CurrentUserService;
import com.proposalservice.proposalservice.service.ProposalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final TaskClient taskClient;
    private final UserClient userClient;
    private final CurrentUserService currentUserService;

    @Override
    @Transactional
    public ProposalResponse createProposal(ProposalRequest request) {
        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        if (!"PROFESSIONAL".equals(currentRole)) {
            throw new BadRequestException("Only professionals can submit proposals");
        }

        TaskFeignResponse task = getTaskOrThrow(request.getTaskId());

        if (!"OPEN".equals(task.status())) {
            throw new BadRequestException("Cannot submit proposal to task that is not open");
        }

        if (task.clientId().equals(currentUserId)) {
            throw new BadRequestException("Client cannot submit proposal to their own task");
        }

        if (proposalRepository.existsByTaskIdAndProfessionalId(request.getTaskId(), currentUserId)) {
            throw new ConflictException("You have already submitted a proposal for this task");
        }

        Proposal proposal = ProposalMapper.toEntity(request, currentUserId);
        Proposal saved = proposalRepository.save(proposal);

        UserFeignResponse professional = getUserQuietly(currentUserId);
        return ProposalMapper.toResponse(saved, task, professional);
    }

    @Override
    @Transactional
    public ProposalResponse updateStatus(Long proposalId, ProposalStatusUpdateRequest request) {
        Proposal proposal = findOrThrow(proposalId);

        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        TaskFeignResponse task = getTaskOrThrow(proposal.getTaskId());

        boolean isOwner = task.clientId().equals(currentUserId);
        if (!"ADMIN".equals(currentRole) && (!"CLIENT".equals(currentRole) || !isOwner)) {
            throw new BadRequestException("Only the task owner or an admin can update proposal status");
        }

        ProposalStatus newStatus = request.getStatus();

        switch (newStatus) {
            case ACCEPTED -> {
                proposal.accept();
                // Assign the professional to the task via task-service
                AssignProfessionalRequest assignReq = new AssignProfessionalRequest();
                assignReq.setProfessionalId(proposal.getProfessionalId());
                taskClient.assignProfessional(proposal.getTaskId(), assignReq);
            }
            case REJECTED -> proposal.reject();
            case PENDING -> proposal.resetToPending();
        }

        Proposal saved = proposalRepository.save(proposal);
        return ProposalMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProposalResponse getProposal(Long id) {
        Proposal proposal = findOrThrow(id);
        TaskFeignResponse task = getTaskQuietly(proposal.getTaskId());
        UserFeignResponse professional = getUserQuietly(proposal.getProfessionalId());
        return ProposalMapper.toResponse(proposal, task, professional);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalResponse> getProposals(Long taskId, Long professionalId) {
        List<Proposal> proposals;
        if (taskId != null) {
            proposals = proposalRepository.findByTaskId(taskId);
        } else if (professionalId != null) {
            proposals = proposalRepository.findByProfessionalId(professionalId);
        } else {
            proposals = proposalRepository.findAll();
        }
        return proposals.stream()
                .map(ProposalMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalResponse> getProposalsForCurrentUser() {
        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        if ("PROFESSIONAL".equals(currentRole)) {
            return proposalRepository.findByProfessionalId(currentUserId).stream()
                    .map(ProposalMapper::toResponse)
                    .toList();
        }

        // CLIENT / ADMIN: return proposals for all tasks they own
        return proposalRepository.findAll().stream()
                .filter(p -> {
                    TaskFeignResponse task = getTaskQuietly(p.getTaskId());
                    return task != null && task.clientId().equals(currentUserId);
                })
                .map(ProposalMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalResponse> getProposalsForTask(Long taskId) {
        Long currentUserId = currentUserService.getCurrentUserId();
        String currentRole = currentUserService.getCurrentUserRole();

        TaskFeignResponse task = getTaskOrThrow(taskId);

        if (!"ADMIN".equals(currentRole) && !task.clientId().equals(currentUserId)) {
            throw new BadRequestException("You are not allowed to view proposals for this task");
        }

        return proposalRepository.findByTaskId(taskId).stream()
                .map(ProposalMapper::toResponse)
                .toList();
    }

    private Proposal findOrThrow(Long id) {
        return proposalRepository.findById(id)
                .orElseThrow(() -> new ProposalNotFoundException(id));
    }

    private TaskFeignResponse getTaskOrThrow(Long taskId) {
        try {
            return taskClient.getTask(taskId);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
    }

    private TaskFeignResponse getTaskQuietly(Long taskId) {
        try {
            return taskClient.getTask(taskId);
        } catch (Exception e) {
            return null;
        }
    }

    private UserFeignResponse getUserQuietly(Long userId) {
        try {
            return userClient.getUser(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
