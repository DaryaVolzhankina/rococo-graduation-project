package guru.qa.niffler.db.mapper;

import guru.qa.niffler.db.model.userdata.UserDataUserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserEntityUserDataRowMapper implements RowMapper<UserDataUserEntity> {

    public static final UserEntityUserDataRowMapper INSTANCE = new UserEntityUserDataRowMapper();

    @Override
    public UserDataUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDataUserEntity user = new UserDataUserEntity();
        user.setId((UUID) rs.getObject("id"));
        user.setUsername(rs.getString("username"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setAvatar(rs.getBytes("avatar"));
        return user;
    }
}
