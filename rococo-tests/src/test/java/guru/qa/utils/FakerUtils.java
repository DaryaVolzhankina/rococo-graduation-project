package guru.qa.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static String generateRandomUsername() {
        return faker.name().username();
    }

    public static String generateRandomPassword() {
        return faker.bothify("????####");
    }

    public static String generateRandomName() {
        return faker.name().firstName();
    }

    public static String generateRandomSurname() {
        return faker.name().lastName();
    }

    public static String generateRandomSentence(int wordsCount) {
        return faker.lorem().sentence(wordsCount);
    }

    public static String generateArtistName() {
        return faker.artist().name();
    }
}