package guru.qa.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDataUserJson {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("avatar")
    private String avatar;
}
