package guru.qa.rococo.controller;

import guru.qa.rococo.model.MuseumJson;
import guru.qa.rococo.service.MuseumService;
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
@RequestMapping("/api/museum")
public class MuseumController {

    private final MuseumService museumService;

    @Autowired
    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping("/{id}")
    public MuseumJson getMuseumById(@PathVariable UUID id) {
        return museumService.getMuseumById(id);
    }

    @PostMapping()
    public MuseumJson addMuseum(@RequestBody MuseumJson museumJson) {
        return museumService.addMuseum(museumJson);
    }

    @PatchMapping()
    public MuseumJson editMuseum(@RequestBody MuseumJson museumJson) {
        return museumService.editMuseum(museumJson);
    }

    @GetMapping()
    public Page<MuseumJson> getAllMuseums(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
        return museumService.getAllMuseums(title, pageable);
    }
}
