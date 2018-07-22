package com.umssonline.auth.services;

import com.umssonline.auth.models.entity.User;
import com.umssonline.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;


    public Collection<User> loadAll() {
        return userRepository.findAll();
    }

    public User detail(Long id) throws Exception {

        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }
        return userFromDb.get();
    }

    @Transactional
    public User login(String account, String password) throws Exception {

        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }
        userFromDb.get().setIsLogged(true);

        return userRepository.save(userFromDb.get());
    }

    public boolean logout(String account, String password) throws Exception {
        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }

        userFromDb.get().setIsLogged(false);

        userRepository.save(userFromDb.get());
        return true;
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User edit(User user) throws Exception {
        Optional<User> userFromDb = userRepository.findById(user.getId());

        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }

        User editedUser = userFromDb.get();

        copyUserEntity(editedUser, user);

        return userRepository.save(editedUser);
    }

    @Transactional
    public boolean unregister(Long id) throws Exception {
        userRepository.deleteById(id);
        return true;
    }


    private void copyUserEntity(User target, User source) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setLastName(source.getLastName());
        target.setNickName(source.getNickName());
        target.setBirthDate(source.getBirthDate());
        target.setAccount(source.getAccount());
        target.setPassword(source.getPassword());
    }
}
