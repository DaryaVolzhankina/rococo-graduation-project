package guru.qa.niffler.db.repository.user;


import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataUserEntity;

public interface UserRepository {
    void createUserForTest(AuthUserEntity user);
    void removeAfterTest(AuthUserEntity user);
    UserDataUserEntity getUserFromUserDataByUsername(String username);
    void updateUserInUserData(UserDataUserEntity user);
}