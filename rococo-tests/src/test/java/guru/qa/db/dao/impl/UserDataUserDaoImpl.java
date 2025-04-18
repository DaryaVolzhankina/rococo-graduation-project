package guru.qa.db.dao.impl;

import guru.qa.db.ServiceDb;
import guru.qa.db.dao.UserDataUserDAO;
import guru.qa.db.model.userdata.UserDataUserEntity;
import guru.qa.db.DataSourceProvider;
import guru.qa.db.mapper.UserEntityUserDataRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class UserDataUserDaoImpl implements UserDataUserDAO {
    private final TransactionTemplate userdataTtpl;
    private final JdbcTemplate userdataJdbcTemplate;

    public UserDataUserDaoImpl() {
        JdbcTransactionManager userdataTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDb.USERDATA));

        this.userdataTtpl = new TransactionTemplate(userdataTm);
        this.userdataJdbcTemplate = new JdbcTemplate(userdataTm.getDataSource());
    }

    @Override
    public int createUserInUserData(UserDataUserEntity user) {
        return userdataJdbcTemplate.update("INSERT INTO users (username) VALUES (?)",
                user.getUsername());
    }

    @Override
    public void deleteUserInUserData(UserDataUserEntity user) {
        userdataJdbcTemplate.update("DELETE FROM users WHERE username = ?", user.getUsername());
    }

    @Override
    public UserDataUserEntity getUserFromUserDataByUsername(String username) {
        return userdataJdbcTemplate.queryForObject("SELECT * FROM public.users WHERE username = ?", UserEntityUserDataRowMapper.INSTANCE, username);
    }

    @Override
    public void updateUserInUserData(UserDataUserEntity user) {
        userdataJdbcTemplate.update("UPDATE users SET  firstname = ?, lastname = ?, avatar = ? WHERE id = ?",
               user.getFirstname(), user.getLastname(), user.getAvatar(), user.getId());
    }
}
