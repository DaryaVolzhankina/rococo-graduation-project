package guru.qa.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.db.model.museum.MuseumEntity;
import guru.qa.db.repository.museum.MuseumRepository;
import guru.qa.db.repository.museum.MuseumRepositorySpringJdbc;
import guru.qa.jupiter.annotation.GeneratedMuseum;
import guru.qa.jupiter.annotation.Museum;
import guru.qa.jupiter.annotation.Painting;
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

public class MuseumExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            NESTED = ExtensionContext.Namespace.create(GeneratedMuseum.MuseumSelector.NESTED),
            OUTER = ExtensionContext.Namespace.create(GeneratedMuseum.MuseumSelector.OUTER);


    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<String, Museum> usersForTest = museumForTest(extensionContext);
        for (Map.Entry<String, Museum> entry : usersForTest.entrySet()) {
            MuseumEntity user = createMuseumForTest();
            extensionContext.getStore(entry.getKey().contains(GeneratedMuseum.MuseumSelector.NESTED.name()) ? NESTED : OUTER)
                    .put(extensionContext.getUniqueId(), user);
        }
    }


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        Museum museumAnnotation = context.getRequiredTestMethod().getAnnotation(Museum.class);
        if (paintingAnnotation != null || museumAnnotation != null) {
            MuseumRepository museumRepository = new MuseumRepositorySpringJdbc();
            MuseumEntity outerMuseum = (MuseumEntity) context.getStore(OUTER).get(context.getUniqueId());
            MuseumEntity nestedMuseum = (MuseumEntity) context.getStore(NESTED).get(context.getUniqueId());
            if (nestedMuseum != null) museumRepository.deleteMuseumAndCity(nestedMuseum);
            if (outerMuseum != null) museumRepository.deleteMuseumAndCity(outerMuseum);
        }
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(GeneratedMuseum.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(MuseumEntity.class);
    }

    @Override
    public MuseumEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        GeneratedMuseum generatedUser = parameterContext.getParameter().getAnnotation(GeneratedMuseum.class);
        return extensionContext.getStore(ExtensionContext.Namespace.create(generatedUser.museumSelector()))
                .get(extensionContext.getUniqueId(), MuseumEntity.class);
    }

    protected MuseumEntity createMuseumForTest() {
        MuseumRepository museumRepository = new MuseumRepositorySpringJdbc();
        MuseumEntity museum = new MuseumEntity();
        String name = new Faker().company().name();
        String description = name + " - величайший музей искусства";
        museum.setTitle(name);
        museum.setDescription(description);
        museum.setPhoto(imageByClasspath("images/museum.png").getBytes(StandardCharsets.UTF_8));
        museum.setGeo(museumRepository.createRandomCity().getId());
        museumRepository.createMuseum(museum);
        return museum;
    }

    private Map<String, Museum> museumForTest(ExtensionContext context) {
        Map<String, Museum> result = new HashMap<>();
        Painting paintingAnnotation = context.getRequiredTestMethod().getAnnotation(Painting.class);
        if (paintingAnnotation != null && paintingAnnotation.museum().handleAnnotation()) {
            result.put(context.getUniqueId() + GeneratedMuseum.MuseumSelector.NESTED, paintingAnnotation.museum());
        }
        Museum museumAnnotation = context.getRequiredTestMethod().getAnnotation(Museum.class);
        if (museumAnnotation != null && museumAnnotation.handleAnnotation()) {
            result.put(context.getUniqueId() + GeneratedMuseum.MuseumSelector.OUTER, museumAnnotation);
        }
        return result;
    }
}
