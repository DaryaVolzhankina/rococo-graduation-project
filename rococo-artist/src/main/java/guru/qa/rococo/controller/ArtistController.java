package guru.qa.rococo.controller;

import guru.qa.rococo.model.ArtistJson;
import guru.qa.rococo.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private static final Logger LOG = LoggerFactory.getLogger(ArtistController.class);

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/{id}")
    public ArtistJson getArtist(@PathVariable UUID id) {
        return artistService.getArtistById(id);
    }

    @PostMapping()
    public ArtistJson addArtist(@RequestBody ArtistJson artist) {
        return artistService.addArtist(artist);
    }

    @PatchMapping()
    public ArtistJson editArtist(@RequestBody ArtistJson artist) {
        return artistService.editArtist(artist);
    }

    @GetMapping()
    public Page<ArtistJson> getAll(@RequestParam(required = false) String name,
                                   @PageableDefault Pageable pageable) {
        return artistService.getAll(name, pageable);
    }
}
