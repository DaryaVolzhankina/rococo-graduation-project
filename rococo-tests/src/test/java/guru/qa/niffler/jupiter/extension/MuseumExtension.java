package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import guru.qa.niffler.db.model.museum.MuseumEntity;
import guru.qa.niffler.db.repository.museum.MuseumRepository;
import guru.qa.niffler.db.repository.museum.MuseumRepositorySpringJdbc;
import guru.qa.niffler.jupiter.annotation.GeneratedMuseum;
import guru.qa.niffler.jupiter.annotation.Museum;
import guru.qa.niffler.jupiter.annotation.Painting;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static guru.qa.niffler.utils.FakerUtils.generateRandomPhoto;

public class MuseumExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace
            NESTED = ExtensionContext.Namespace.create(GeneratedMuseum.Selector.NESTED),
            OUTER = ExtensionContext.Namespace.create(GeneratedMuseum.Selector.OUTER);

//    @Override
//    public void beforeEach(ExtensionContext context) throws Exception {
//        Museum annotation = context.getRequiredTestMethod().getAnnotation(Museum.class);
//        if (annotation != null && annotation.handleAnnotation()) {
//            MuseumRepository museumRepository = new MuseumRepositorySpringJdbc();
//            MuseumEntity museum = new MuseumEntity();
//            String name = annotation.title().isEmpty() ? new Faker().company().name() : annotation.title();
//            String photo = annotation.photo().isEmpty() ? generateRandomPhoto() : annotation.photo();
//            String description = annotation.description().isEmpty() ? name + " - величайший музей искусства" : annotation.description();
//            UUID geo = annotation.geo().isEmpty() ? museumRepository.createRandomCity().getId() : UUID.fromString(annotation.geo());
//            museum.setTitle(name);
//            museum.setDescription(description);
//            museum.setPhoto(photo.getBytes(StandardCharsets.UTF_8));
//            museum.setGeo(geo);
//            museumRepository.createMuseum(museum);
//            context.getStore(NAMESPACE).put(context.getUniqueId(), museum);
//        }
//    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Map<String, Museum> museumForTest = museumForTest(extensionContext);
        for (Map.Entry<String, Museum> entry : museumForTest.entrySet()) {
            MuseumEntity museum = createMuseumForTest();
            extensionContext.getStore(entry.getKey().contains(GeneratedMuseum.Selector.NESTED.name()) ? NESTED : OUTER)
                    .put(extensionContext.getUniqueId(), museum);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        MuseumRepository museumRepository = new MuseumRepositorySpringJdbc();
        MuseumEntity nestedMuseum = (MuseumEntity) context.getStore(NESTED).get(context.getUniqueId());
        MuseumEntity outerMuseum = (MuseumEntity) context.getStore(OUTER).get(context.getUniqueId());
        if (nestedMuseum != null) museumRepository.deleteMuseumAndCity(nestedMuseum);
        if (outerMuseum != null) museumRepository.deleteMuseumAndCity(outerMuseum);
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(GeneratedMuseum.class) &&
                parameterContext.getParameter().getType().isAssignableFrom(MuseumEntity.class);
    }

    @Override
    public MuseumEntity resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        GeneratedMuseum generatedMuseum = parameterContext.getParameter().getAnnotation(GeneratedMuseum.class);
        return extensionContext.getStore(ExtensionContext.Namespace.create(generatedMuseum.selector()))
                .get(generatedMuseum.selector(), MuseumEntity.class);
    }

    private Map<String, Museum> museumForTest(ExtensionContext context) {
        Map<String, Museum> result = new HashMap<>();
        Painting panting = context.getRequiredTestMethod().getAnnotation(Painting.class);
        if (panting != null && panting.museum().handleAnnotation()) {
            result.put(context.getUniqueId() + GeneratedMuseum.Selector.NESTED, panting.museum());
        }
        Museum museum = context.getRequiredTestMethod().getAnnotation(Museum.class);
        if (museum != null && museum.handleAnnotation()) {
            result.put(context.getUniqueId() + GeneratedMuseum.Selector.OUTER, museum);
        }
        return result;
    }

    protected MuseumEntity createMuseumForTest() {
        MuseumRepository museumRepository = new MuseumRepositorySpringJdbc();
        MuseumEntity museum = new MuseumEntity();
        String name = new Faker().company().name();
        String description = name + " - величайший музей искусства";
        museum.setTitle(name);
        museum.setDescription(description);
        museum.setPhoto(generateRandomPhoto().getBytes(StandardCharsets.UTF_8));
        museum.setGeo(museumRepository.createRandomCity().getId());
        museumRepository.createMuseum(museum);
        return museum;
    }
}
