package com.userservice.userservice.service.impl;

import com.userservice.userservice.dto.*;
import com.userservice.userservice.exception.EmailAlreadyExistsException;
import com.userservice.userservice.exception.UserNotFoundException;
import com.userservice.userservice.mapper.UserMapper;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserRole;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        return saveAndMap(UserMapper.toEntity(request));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(getUserOrThrow(id));
    }

    @Override
    public List<UserResponse> getUsers(UserRole role) {
        return (role != null
                ? userRepository.findByRole(role)
                : userRepository.findAll())
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getCurrentUser(Long userId) {
        return UserMapper.toResponse(getUserOrThrow(userId));
    }

    public UserResponse updateProfessionalProfile(Long userId,
                                                  ProfessionalProfileRequest request) {

        User user = getUserOrThrow(userId);
        UserMapper.updateProfessionalProfile(user, request);
        return saveAndMap(user);
    }

    @Override
    public UserResponse updateUserStatus(Long id, UserStatusRequest request) {

        User user = getUserOrThrow(id);
        UserMapper.updateUserStatus(user, request);
        return saveAndMap(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserOrThrow(id));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserResponse saveAndMap(User user) {
        return UserMapper.toResponse(userRepository.save(user));
    }
}

//======================================================================================================================
/*
package com.userservice.userservice.service.impl;

import com.userservice.userservice.dto.*;
        import com.userservice.userservice.exception.EmailAlreadyExistsException;
import com.userservice.userservice.exception.UserNotFoundException;
import com.userservice.userservice.mapper.UserMapper;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserRole;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = UserMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return UserMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getUsers(UserRole role) {

        List<User> users;

        if (role != null) {
            users = userRepository.findByRole(role);
        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getCurrentUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserMapper.toResponse(user);
    }

    public UserResponse updateProfessionalProfile(
            Long userId,
            ProfessionalProfileRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserMapper.updateProfessionalProfile(user, request);

        User updatedUser = userRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponse updateUserStatus(Long id, UserStatusRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        UserMapper.updateUserStatus(user, request);

        User updatedUser = userRepository.save(user);

        return UserMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}
*/

//=======================================================================================================================
/*
package com.userservice.userservice.service.impl;

import com.userservice.userservice.dto.*;
        import com.userservice.userservice.exception.EmailAlreadyExistsException;
import com.userservice.userservice.exception.UserNotFoundException;
import com.userservice.userservice.mapper.UserMapper;
import com.userservice.userservice.model.User;
import com.userservice.userservice.model.UserRole;
import com.userservice.userservice.repository.UserRepository;
import com.userservice.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserServiceImpl(UserRepository userRepository) implements UserService {

    @Override
    public UserResponse createUser(UserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        return saveAndMap(UserMapper.toEntity(request));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return UserMapper.toResponse(getUserOrThrow(id));
    }

    @Override
    public List<UserResponse> getUsers(UserRole role) {
        return (role != null
                ? userRepository.findByRole(role)
                : userRepository.findAll())
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    public UserResponse getCurrentUser(Long userId) {
        return UserMapper.toResponse(getUserOrThrow(userId));
    }

    public UserResponse updateProfessionalProfile(Long userId,
                                                  ProfessionalProfileRequest request) {

        User user = getUserOrThrow(userId);
        UserMapper.updateProfessionalProfile(user, request);
        return saveAndMap(user);
    }

    @Override
    public UserResponse updateUserStatus(Long id, UserStatusRequest request) {

        User user = getUserOrThrow(id);
        UserMapper.updateUserStatus(user, request);
        return saveAndMap(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserOrThrow(id));
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private UserResponse saveAndMap(User user) {
        return UserMapper.toResponse(userRepository.save(user));
    }
}
*/

