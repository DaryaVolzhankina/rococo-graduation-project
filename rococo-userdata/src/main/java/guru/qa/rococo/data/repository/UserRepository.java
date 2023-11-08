package guru.qa.rococo.data.repository;

import guru.qa.rococo.data.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nullable;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Nullable
    UserEntity findByUsername(String username);
}
