package guru.qa.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class DataUtils {
    public static String imageByClasspath(String imageClasspath) {
        ClassLoader classLoader = DataUtils.class.getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(imageClasspath)) {
            assert is != null;
            String fileExtension = imageClasspath.substring(imageClasspath.lastIndexOf(".") + 1);
            byte[] base64Image = Base64.getEncoder().encode(is.readAllBytes());
            return "data:image/" + fileExtension + ";base64," + new String(base64Image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
