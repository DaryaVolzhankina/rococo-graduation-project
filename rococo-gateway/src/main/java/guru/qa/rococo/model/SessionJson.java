package guru.qa.rococo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionJson {

    @JsonProperty("username")
    private String username;

    public SessionJson() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
