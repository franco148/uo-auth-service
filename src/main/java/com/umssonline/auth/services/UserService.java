package com.umssonline.auth.services;

import com.umssonline.auth.models.User;
import com.umssonline.auth.repositories.AuthDao;
import com.umssonline.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {

//    @Resource
//    private AuthDao authDao;

    @Resource
    private UserRepository userRepository;


    public Collection<User> loadAll() {
        //return authDao.load(User.class);
        return userRepository.findAll();
    }

    public User detail(Long id) throws Exception {
        //return authDao.find(User.class, id);
        //return userRepository.getOne(id);
        Optional<User> userFromDb = userRepository.findById(id);
        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }
        return userFromDb.get();
    }

    @Transactional
    public User login(String account, String password) throws Exception {
//        String jpqlGetUserByEmail = String.format("SELECT u FROM User u WHERE u.account = %s and u.password = %s", account, password);
////        Collection<User> userListResult = authDao.load(jpqlGetUserByEmail, User.class);
////
////        if (userListResult == null || !userListResult.stream().findFirst().isPresent()) {
////            throw new Exception("Invalid User or Password");
////        }
////
////        User logginUser = userListResult.stream().findFirst().get();
////        logginUser.setLogged(true);
////
////        return authDao.persist(logginUser);
        Optional<User> userFromDb = userRepository.findByAccountAndPassword(account, password);
        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }
        userFromDb.get().setLogged(true);

        return userRepository.save(userFromDb.get());
    }

    public User register(User user) {
        //return authDao.persist(user);
        return userRepository.save(user);
    }

    @Transactional
    public User edit(User user) throws Exception {
        //User userFromDb = authDao.find(User.class, user.getId());
        Optional<User> userFromDb = userRepository.findById(user.getId());

        if (!userFromDb.isPresent()) {
            throw new Exception("User does not exist.");
        }

        User editedUser = userFromDb.get();

        copyUserEntity(editedUser, user);

        //return authDao.update(user);
        return userRepository.save(editedUser);
    }

    @Transactional
    public boolean unregister(Long id) throws Exception {
        //User userFromDb = authDao.find(User.class, id);
//        Optional<User> userFromDb = userRepository.findById(id);
//
//        if (!userFromDb.isPresent()) {
//            throw new Exception("User does not exist.");
//        }

        //authDao.delete(userFromDb);
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
