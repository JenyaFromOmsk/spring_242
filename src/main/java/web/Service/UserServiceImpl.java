package web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.UserDao;
import web.Models.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        if (user.getPassword().length() <= 32) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDao.addUser(user);
        }
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userDao.deleteUserId(id);
    }

    @Override
    @Transactional
    public void editUser(User user) {
        if (user.getPassword().length() < 32) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDao.editUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserId(Long id) {
        return userDao.getUserId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userDao.getLogin(login);
    }
}