package guru.qa.rococo.controller;

import guru.qa.rococo.model.PaintingJson;
import guru.qa.rococo.service.PaintingService;
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
@RequestMapping("/api/painting")
public class PaintingController {

    private final PaintingService paintingService;

    @Autowired
    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/{id}")
    public PaintingJson getPainting(@PathVariable UUID id) {
        return paintingService.getPaintingById(id);
    }

    @PostMapping()
    public PaintingJson addPainting(@RequestBody PaintingJson paintingJson) {
        return paintingService.addPainting(paintingJson);
    }

    @PatchMapping()
    public PaintingJson editPainting(@RequestBody PaintingJson paintingJson) {
        return paintingService.editPainting(paintingJson);
    }

    @GetMapping
    public Page<PaintingJson> getAllPaintings(@RequestParam(required = false) String title,
                                              @PageableDefault Pageable pageable) {
        return paintingService.getAll(title, pageable);
    }

    @GetMapping("/author/{artistId}")
    public Page<PaintingJson> getAllPaintingsByAuthor(@PathVariable UUID artistId,
                                              @PageableDefault Pageable pageable) {
        return paintingService.getAllPaintingsByArtist(artistId, pageable);
    }
}
