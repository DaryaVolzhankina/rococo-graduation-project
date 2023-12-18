package guru.qa.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.db.model.artist.ArtistEntity;
import guru.qa.db.model.museum.MuseumEntity;
import guru.qa.db.model.painting.PaintingEntity;
import guru.qa.db.repository.painting.PaintingRepository;
import guru.qa.db.repository.painting.PaintingRepositorySpringJdbc;
import guru.qa.jupiter.annotation.Museum;
import guru.qa.jupiter.annotation.Painting;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.nio.charset.StandardCharsets;

import static guru.qa.utils.DataUtils.imageByClasspath;
import static guru.qa.utils.FakerUtils.generateRandomSentence;

public class PaintingExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(PaintingExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        if (paintingAnnotation != null) {
            PaintingRepository paintingRepository = new PaintingRepositorySpringJdbc();
            PaintingEntity painting = generateRandomPainting();
            painting.setArtist(context.getStore(ArtistExtension.NESTED).get(context.getUniqueId(), ArtistEntity.class).getId());
            createMuseumIfPresent(paintingAnnotation, painting, context);
            paintingRepository.createPainting(painting);
            context.getStore(NAMESPACE).put(context.getUniqueId(), painting);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        if (paintingAnnotation != null) {
            PaintingRepository paintingRepository = new PaintingRepositorySpringJdbc();
            PaintingEntity painting = context.getStore(NAMESPACE).get(context.getUniqueId(), PaintingEntity.class);
            paintingRepository.deletePainting(painting);
        }
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(PaintingEntity.class);
    }

    @Override
    public PaintingEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), PaintingEntity.class);
    }


    private void createMuseumIfPresent(Painting annotation, PaintingEntity paintingEntity, ExtensionContext context) {
        Museum museum = annotation.museum();
        if (museum.handleAnnotation()) {
            MuseumEntity museumEntity = (MuseumEntity) context.getStore(MuseumExtension.NESTED).get(context.getUniqueId());
            paintingEntity.setMuseum(museumEntity.getId());
        }
    }

    private PaintingEntity generateRandomPainting() {
        Faker faker = new Faker();
        PaintingEntity paintingEntity = new PaintingEntity();
        paintingEntity.setTitle(faker.ancient().hero());
        paintingEntity.setDescription(generateRandomSentence(6));
        paintingEntity.setContent(imageByClasspath("images/scream.jpg").getBytes(StandardCharsets.UTF_8));
        return paintingEntity;
    }
}
