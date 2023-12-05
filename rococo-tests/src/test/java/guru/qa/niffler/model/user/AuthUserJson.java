package guru.qa.niffler.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthUserJson {
    @JsonProperty("username")
    private String username;

    public AuthUserJson() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
