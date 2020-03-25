package com.umssonline.auth.service;

import com.umssonline.auth.controller.dto.request.RegisterUserDto;
import com.umssonline.auth.controller.dto.request.UpdateUserDto;
import com.umssonline.auth.controller.dto.response.UserResponseDto;
import com.umssonline.auth.repository.domain.User;
import com.umssonline.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    //region Properties
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    //endregion

    //region Constructors
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    @Transactional(readOnly = true)
    public Collection<UserResponseDto> loadAll() {
        Collection<UserResponseDto> userCollection = userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDto response = modelMapper.map(user, UserResponseDto.class);
                    return response;
                })
                .collect(Collectors.toList());

        return userCollection;
    }

    @Transactional(readOnly = true)
    public UserResponseDto detail(Long id) {

        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            String errorMessage = "User with id=%s does not exist.";
            log.error(errorMessage, id);
            throw new EntityNotFoundException(String.format(errorMessage, id));
        }

        UserResponseDto userResponse = modelMapper.map(userFromDb.get(), UserResponseDto.class);
        return userResponse;
    }

    @Transactional
    public UserResponseDto login(String account, String password) {

        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            String errorMessage = "Invalid user name or password.";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        userFromDb.get().setIsLogged(true);
        User loggedUser = userRepository.save(userFromDb.get());

        UserResponseDto userResponse = modelMapper.map(loggedUser, UserResponseDto.class);

        return userResponse;
    }

    @Transactional
    public boolean logout(String account, String password) {
        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            String errorMessage = "Invalid user name or password.";
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }

        userFromDb.get().setIsLogged(false);
        userRepository.save(userFromDb.get());
        return true;
    }

    @Transactional
    public UserResponseDto register(RegisterUserDto userDto) {
        User userToSave = modelMapper.map(userDto, User.class);
        userToSave.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User savedUser = userRepository.saveAndFlush(userToSave);
        UserResponseDto userResponse = modelMapper.map(savedUser, UserResponseDto.class);
        return userResponse;
    }

    @Transactional
    public UserResponseDto edit(Long userId, UpdateUserDto userDto) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }
        User userToBeEdited = modelMapper.map(userDto, User.class);
        userToBeEdited.setId(userId);
        copyUserEntity(userToBeEdited, userFromDb.get());

        User updatedUser = userRepository.saveAndFlush(userToBeEdited);
        UserResponseDto userResponse = modelMapper.map(updatedUser, UserResponseDto.class);
        return userResponse;
    }

    @Transactional
    public boolean unregister(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void confirmSubscription(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);

        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }

        User confirmedUser = userFromDb.get();
        confirmedUser.setIsEnabled(true);
        userRepository.save(confirmedUser);
    }


    private void copyUserEntity(User target, User source) {
        target.setIsEnabled(source.getIsEnabled());
        target.setIsDeleted(source.getIsDeleted());
        target.setIsLogged(source.getIsLogged());
        target.setCreatedAt(source.getCreatedAt());

        target.getUserRoles().forEach(role -> role.setIsDeleted(false));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByAccount(username);
        if (null == foundUser) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
            foundUser.getAccount(),
                foundUser.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>()
        );
    }
}
