package guru.qa.rococo.service;

import guru.qa.rococo.data.UserEntity;
import guru.qa.rococo.data.repository.UserRepository;
import guru.qa.rococo.model.UserJson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import guru.qa.rococo.ex.NotFoundException;

import java.nio.charset.StandardCharsets;

@Component
public class UserDataService {

    private static final Logger LOG = LoggerFactory.getLogger(UserDataService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "users", groupId = "userdata")
    public void listener(@Payload UserJson user, ConsumerRecord<String, UserJson> cr) {
        LOG.info("### Kafka topic [users] received message: " + user.getUsername());
        LOG.info("### Kafka consumer record: " + cr.toString());
        UserEntity userDataEntity = new UserEntity();
        userDataEntity.setUsername(user.getUsername());
        UserEntity userEntity = userRepository.save(userDataEntity);
        LOG.info(String.format(
                "### User '%s' successfully saved to database with id: %s",
                user.getUsername(),
                userEntity.getId()
        ));
    }

    public @Nonnull
    UserJson updateUser(@Nonnull String username, @Nonnull UserJson user) {
        UserEntity userDataEntity = getRequiredUser(username);
        userDataEntity.setFirstname(user.getFirstname());
        userDataEntity.setLastname(user.getLastname());
        userDataEntity.setAvatar(user.getAvatar() != null ? user.getAvatar().getBytes(StandardCharsets.UTF_8) : null);
        UserEntity saved = userRepository.save(userDataEntity);
        return UserJson.fromEntity(saved);
    }

    public @Nonnull
    UserJson getUser(String username) {
        return UserJson.fromEntity(getRequiredUser(username));
    }

    @Nonnull
    UserEntity getRequiredUser(@Nonnull String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException("Can`t find user by username: " + username);
        }
        return user;
    }
}
