package guru.qa.db.repository.user;

import guru.qa.db.dao.AuthUserDao;
import guru.qa.db.dao.UserDataUserDAO;
import guru.qa.db.dao.impl.AuthUserDaoImpl;
import guru.qa.db.dao.impl.UserDataUserDaoImpl;
import guru.qa.db.model.userdata.UserDataUserEntity;
import guru.qa.db.model.auth.AuthUserEntity;

public class UserRepositorySpringJDBC implements UserRepository{
    private final AuthUserDao authUserDAO;
    private final UserDataUserDAO udUserDAO;

    public UserRepositorySpringJDBC() {
        this.authUserDAO = new AuthUserDaoImpl();
        this.udUserDAO = new UserDataUserDaoImpl();
    }

    @Override
    public void createUserForTest(AuthUserEntity user) {
        authUserDAO.createUserInAuth(user);
        udUserDAO.createUserInUserData(fromAuthUser(user));
    }

    @Override
    public void removeAfterTest(AuthUserEntity user) {
        UserDataUserEntity userInUd = udUserDAO.getUserFromUserDataByUsername(user.getUsername());
        udUserDAO.deleteUserInUserData(userInUd);
        authUserDAO.deleteUser(user);
    }

    @Override
    public UserDataUserEntity getUserFromUserDataByUsername(String username) {
        return udUserDAO.getUserFromUserDataByUsername(username);
    }

    public void updateUserInUserData(UserDataUserEntity user){
        udUserDAO.updateUserInUserData(user);
    }

    private UserDataUserEntity fromAuthUser(AuthUserEntity user) {
        UserDataUserEntity userdataUser = new UserDataUserEntity();
        userdataUser.setUsername(user.getUsername());
        return userdataUser;
    }}