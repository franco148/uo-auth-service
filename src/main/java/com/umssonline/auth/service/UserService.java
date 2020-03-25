package com.umssonline.auth.service;

import com.umssonline.auth.repository.domain.User;
import com.umssonline.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public Collection<User> loadAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User detail(Long id) {

        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }
        return userFromDb.get();
    }

    @Transactional
    public User login(String account, String password) throws EntityNotFoundException {

        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }
        userFromDb.get().setIsLogged(true);

        return userRepository.save(userFromDb.get());
    }

    @Transactional
    public boolean logout(String account, String password) throws EntityNotFoundException {
        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }

        userFromDb.get().setIsLogged(false);

        userRepository.save(userFromDb.get());
        return true;
    }

    @Transactional
    public User register(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User edit(User user) throws EntityNotFoundException {
        Optional<User> userFromDb = userRepository.findById(user.getId());

        if (!userFromDb.isPresent()) {
            throw new EntityNotFoundException("User does not exist.");
        }

        copyUserEntity(user, userFromDb.get());

        return userRepository.saveAndFlush(user);
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
}
