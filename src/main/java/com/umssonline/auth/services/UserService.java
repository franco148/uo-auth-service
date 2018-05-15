package com.umssonline.auth.services;

import com.umssonline.auth.models.User;
import com.umssonline.auth.repositories.AuthDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service
public class UserService {

    @Resource
    private AuthDao authDao;


    public Collection<User> loadAll() {
        return authDao.load(User.class);
    }

    public User detail(Long id) {
        return authDao.find(User.class, id);
    }

    public User login(String account, String password) throws Exception {
        String jpqlGetUserByEmail = String.format("SELECT u FROM User u WHERE u.account = %s and u.password = %s", account, password);
        Collection<User> userListResult = authDao.load(jpqlGetUserByEmail, User.class);

        if (userListResult == null || !userListResult.stream().findFirst().isPresent()) {
            throw new Exception("Invalid User or Password");
        }

        User logginUser = userListResult.stream().findFirst().get();
        logginUser.setLogged(true);

        return authDao.persist(logginUser);
    }

    public User register(User user) {
        return authDao.persist(user);
    }

    public User edit(User user) throws Exception {
        User userFromDb = authDao.find(User.class, user.getId());

        if (userFromDb == null) {
            throw new Exception("User does not exist.");
        }

        copyUserEntity(userFromDb, user);

        return authDao.persist(user);
    }

    public boolean unregister(Long id) throws Exception {
        User userFromDb = authDao.find(User.class, id);

        if (userFromDb == null) {
            throw new Exception("User does not exist.");
        }

        authDao.delete(userFromDb);
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
