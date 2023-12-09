package guru.qa.db.repository.user;


import guru.qa.db.model.userdata.UserDataUserEntity;
import guru.qa.db.model.auth.AuthUserEntity;

public interface UserRepository {
    void createUserForTest(AuthUserEntity user);
    void removeAfterTest(AuthUserEntity user);
    UserDataUserEntity getUserFromUserDataByUsername(String username);
    void updateUserInUserData(UserDataUserEntity user);
}