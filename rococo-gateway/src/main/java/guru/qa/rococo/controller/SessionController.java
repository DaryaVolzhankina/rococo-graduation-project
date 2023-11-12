package guru.qa.rococo.controller;

import guru.qa.rococo.model.SessionJson;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/session")
public class SessionController {

    @GetMapping()
    public SessionJson getSession(@AuthenticationPrincipal Jwt principal) {
        if (principal != null) {
            String username = principal.getClaim("sub");
            SessionJson sessionJson = new SessionJson();
            sessionJson.setUsername(username);
            return sessionJson;
        }else{
            return new SessionJson();
        }
    }
}
