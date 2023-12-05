package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.artist.ArtistEntity;
import guru.qa.niffler.db.model.museum.MuseumEntity;
import guru.qa.niffler.db.model.painting.PaintingEntity;
import guru.qa.niffler.db.repository.artist.ArtistRepository;
import guru.qa.niffler.db.repository.artist.ArtistRepositorySpringJdbc;
import guru.qa.niffler.db.repository.painting.PaintingRepository;
import guru.qa.niffler.db.repository.painting.PaintingRepositorySpringJdbc;
import guru.qa.niffler.jupiter.annotation.Artist;
import guru.qa.niffler.jupiter.annotation.Painting;
import guru.qa.niffler.model.artist.ArtistJson;
import guru.qa.niffler.model.paintings.PaintingJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static guru.qa.niffler.jupiter.extension.MuseumExtension.NESTED;
import static guru.qa.niffler.utils.FakerUtils.generateArtistName;
import static guru.qa.niffler.utils.FakerUtils.generateRandomPhoto;


public class ArtistExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ArtistExtension.class);


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Artist annotation = context.getRequiredTestMethod().getAnnotation(Artist.class);
        if (annotation != null) {
            ArtistRepository artistRepository = new ArtistRepositorySpringJdbc();
            ArtistEntity artist = new ArtistEntity();
            String name = generateArtistName();
            String biography = name + " - великий художник. Он написал множество картин";
            artist.setName(name);
            artist.setBiography(biography);
            artist.setPhoto(generateRandomPhoto().getBytes(StandardCharsets.UTF_8));
            artistRepository.createArtist(artist);
            ArtistJson artistJson = ArtistJson.fromEntity(artist);
            artistJson.setPaintings(createPaintingsIfPresent(annotation, artist.getId(), context));
            context.getStore(NAMESPACE).put(context.getUniqueId(), artistJson);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Artist annotation = context.getRequiredTestMethod().getAnnotation(Artist.class);
        if (annotation != null) {
            PaintingRepository paintingRepository = new PaintingRepositorySpringJdbc();
            ArtistRepository artistRepository = new ArtistRepositorySpringJdbc();
            ArtistJson artist = (ArtistJson) context.getStore(NAMESPACE).get(context.getUniqueId());
            paintingRepository.deleteAllArtistsPaintingsById(artist.getId());
            artistRepository.deleteArtist(ArtistJson.toEntity(artist));
        }
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(ArtistJson.class);
    }

    @Override
    public ArtistJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(ArtistExtension.NAMESPACE)
                .get(extensionContext.getUniqueId(), ArtistJson.class);
    }

    private List<PaintingJson> createPaintingsIfPresent(Artist annotation, UUID artistId, ExtensionContext context) {
        PaintingRepository paintingRepository = new PaintingRepositorySpringJdbc();
        List<PaintingJson> paintings = new ArrayList<>();
        Painting painting = annotation.painting();
        if (painting.handleAnnotation()) {
            for (int i = 0; i < painting.count(); i++) {
                PaintingEntity paintingEntity = generateRandomPainting();
                paintingEntity.setArtist(artistId);
                MuseumEntity createdMuseum;
                if (painting.museum().handleAnnotation()) {
                    createdMuseum = context.getStore(NESTED).get(
                            context.getUniqueId(),
                            MuseumEntity.class
                    );
                    paintingEntity.setMuseum(createdMuseum.getId());
                }
                paintingRepository.createPainting(paintingEntity);
                paintings.add(PaintingJson.fromEntity(paintingEntity));
            }
        }
        return paintings;
    }

    private PaintingEntity generateRandomPainting() {
        Faker faker = new Faker();
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(faker.ancient().hero());
        paintingEntity.setDescription("Очень интересное описание картины");
        paintingEntity.setContent(generateRandomPhoto().getBytes(StandardCharsets.UTF_8));
        return paintingEntity;
    }
}

