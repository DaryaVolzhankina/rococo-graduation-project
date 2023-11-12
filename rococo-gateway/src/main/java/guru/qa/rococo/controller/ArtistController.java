package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.api.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistClient;

    @Autowired
    public ArtistController(ArtistService artistClient) {
        this.artistClient = artistClient;
    }

    @GetMapping("/{id}")
    public ArtistJson getArtist(@PathVariable UUID id) {
        return artistClient.getArtist(id);
    }

    @GetMapping()
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                         @PageableDefault Pageable pageable) {
        return artistClient.getAll(name, pageable);
    }

    @PostMapping()
    public ArtistJson addArtist(@AuthenticationPrincipal Jwt principal, @RequestBody ArtistJson artist) {
        return artistClient.addArtist(artist);
    }

    @PatchMapping()
    public ArtistJson editArtist(@AuthenticationPrincipal Jwt principal, @RequestBody ArtistJson artist) {
        return artistClient.editArtist(artist);
    }
}
