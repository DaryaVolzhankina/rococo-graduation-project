package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public interface AuthUserDao {
    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    int createUserInAuth(AuthUserEntity user);
    void deleteUser(AuthUserEntity user);
    AuthUserEntity getUserFromAuthById(UUID userID);
    void updateUserInAuth(AuthUserEntity user);
}
