package guru.qa.rococo.controller;

import guru.qa.rococo.model.CountryJson;
import guru.qa.rococo.service.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    private final MuseumService museumService;

    @Autowired
    public CountryController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping()
    public Page<CountryJson> getAllCountries(@PageableDefault Pageable pageable) {
        return museumService.getAllCountries(pageable);
    }
}
