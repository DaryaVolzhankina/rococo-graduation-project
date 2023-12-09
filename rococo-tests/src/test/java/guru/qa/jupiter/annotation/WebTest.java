package guru.qa.jupiter.annotation;

import guru.qa.jupiter.extension.BrowserExtension;
import guru.qa.jupiter.extension.ApiLoginExtension;
import guru.qa.jupiter.extension.ArtistExtension;
import guru.qa.jupiter.extension.DBUserExtension;
import guru.qa.jupiter.extension.MuseumExtension;
import guru.qa.jupiter.extension.PaintingExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({DBUserExtension.class, ApiLoginExtension.class, MuseumExtension.class, ArtistExtension.class, PaintingExtension.class, BrowserExtension.class})
public @interface WebTest {
}
