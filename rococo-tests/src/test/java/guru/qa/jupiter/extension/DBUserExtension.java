package guru.qa.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.db.dao.AuthUserDao;
import guru.qa.db.dao.UserDataUserDAO;
import guru.qa.db.dao.impl.AuthUserDaoImpl;
import guru.qa.db.dao.impl.UserDataUserDaoImpl;
import guru.qa.db.model.auth.AuthUserEntity;
import guru.qa.db.model.auth.Authority;
import guru.qa.db.model.auth.AuthorityEntity;
import guru.qa.db.model.userdata.UserDataUserEntity;
import guru.qa.jupiter.annotation.DBUser;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Arrays;


public class DBUserExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DBUserExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Faker faker = new Faker();
        AuthUserDao authUserDAO = new AuthUserDaoImpl();
        UserDataUserDAO userDataUserDAO = new UserDataUserDaoImpl();

        DBUser annotation = context.getRequiredTestMethod().getAnnotation(DBUser.class);
        if (annotation != null) {
            AuthUserEntity user = new AuthUserEntity();
            String username = annotation.username().isEmpty() ? faker.name().username() : annotation.username();
            String password = annotation.password().isEmpty() ? faker.internet().password() : annotation.password();
            user.setUsername(username);
            user.setPassword(password);
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setAuthorities(Arrays.stream(Authority.values())
                    .map(a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(user);
                        return ae;
                    }).toList());
            authUserDAO.createUserInAuth(user);
            user.setPassword(password);

            UserDataUserEntity userdataUser = new UserDataUserEntity();
            userdataUser.setUsername(user.getUsername());
            userDataUserDAO.createUserInUserData(userdataUser);

            context.getStore(NAMESPACE).put(context.getUniqueId(), user);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(AuthUserEntity.class);
    }

    @Override
    public AuthUserEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(DBUserExtension.NAMESPACE)
                .get(extensionContext.getUniqueId(), AuthUserEntity.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        DBUser annotation = context.getRequiredTestMethod().getAnnotation(DBUser.class);
        if (annotation != null) {
            AuthUserDao authUserDAO = new AuthUserDaoImpl();
            UserDataUserDAO userDataUserDAO = new UserDataUserDaoImpl();
            AuthUserEntity authUser = ((AuthUserEntity) context.getStore(NAMESPACE).get(context.getUniqueId()));
            userDataUserDAO.deleteUserInUserData(userDataUserDAO.getUserFromUserDataByUsername(authUser.getUsername()));
            authUserDAO.deleteUser(authUser);
        }
    }
}
