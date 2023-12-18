package guru.qa.jupiter.extension;

import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.db.repository.artist.ArtistRepository;
import guru.qa.db.repository.artist.ArtistRepositorySpringJdbc;
import guru.qa.db.repository.painting.PaintingRepository;
import guru.qa.db.repository.painting.PaintingRepositorySpringJdbc;
import guru.qa.jupiter.annotation.Artist;
import guru.qa.jupiter.annotation.GeneratedArtist;
import guru.qa.jupiter.annotation.Painting;
import guru.qa.utils.FakerUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static guru.qa.utils.DataUtils.imageByClasspath;
import static guru.qa.utils.FakerUtils.generateRandomSentence;


public class ArtistExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            NESTED = ExtensionContext.Namespace.create(GeneratedArtist.ArtistSelector.NESTED),
            OUTER = ExtensionContext.Namespace.create(GeneratedArtist.ArtistSelector.OUTER);

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<String, Artist> artistsForTest = artistForTest(extensionContext);
        for (Map.Entry<String, Artist> entry : artistsForTest.entrySet()) {
            ArtistEntity artist = createArtistForTest();
            extensionContext.getStore(entry.getKey().contains(GeneratedArtist.ArtistSelector.NESTED.name()) ? NESTED : OUTER)
                    .put(extensionContext.getUniqueId(), artist);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        Artist artistAnnotation = context.getRequiredTestMethod().getAnnotation(Artist.class);
        if (paintingAnnotation != null || artistAnnotation != null) {
            ArtistRepository artistRepository = new ArtistRepositorySpringJdbc();
            PaintingRepository paintingRepository = new PaintingRepositorySpringJdbc();
            ArtistEntity outerArtist = (ArtistEntity) context.getStore(OUTER).get(context.getUniqueId());
            ArtistEntity nestedArtist = (ArtistEntity) context.getStore(NESTED).get(context.getUniqueId());
            if (nestedArtist != null) {
                paintingRepository.deleteAllPaintingsByArtistId(nestedArtist.getId());
                artistRepository.deleteArtist(nestedArtist);
            }
            if (outerArtist != null) {
                paintingRepository.deleteAllPaintingsByArtistId(outerArtist.getId());
                artistRepository.deleteArtist(outerArtist);
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(GeneratedArtist.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(ArtistEntity.class);
    }

    @Override
    public ArtistEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        GeneratedArtist generatedArtist = parameterContext.getParameter().getAnnotation(GeneratedArtist.class);
        return extensionContext.getStore(ExtensionContext.Namespace.create(generatedArtist.artistSelector()))
                .get(extensionContext.getUniqueId(), ArtistEntity.class);
    }

    private ArtistEntity createArtistForTest() {
        ArtistRepository artistRepository = new ArtistRepositorySpringJdbc();
        ArtistEntity artist = new ArtistEntity();
        String name = FakerUtils.generateRandomUsername();
        String biography = name + generateRandomSentence(6);
        artist.setName(name);
        artist.setBiography(biography);
        artist.setPhoto(imageByClasspath("images/artist.jpg").getBytes(StandardCharsets.UTF_8));
        artistRepository.createArtist(artist);
        return artist;
    }

    private Map<String, Artist> artistForTest(ExtensionContext context) {
        Map<String, Artist> result = new HashMap<>();
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        if (paintingAnnotation != null) {
            result.put(context.getUniqueId() + GeneratedArtist.ArtistSelector.NESTED, paintingAnnotation.artist());
        }
        Artist artistAnnotation = context.getRequiredTestMethod().getAnnotation(Artist.class);
        if (artistAnnotation != null) {
            result.put(context.getUniqueId() + GeneratedArtist.ArtistSelector.OUTER, artistAnnotation);
        }
        return result;
    }
}

