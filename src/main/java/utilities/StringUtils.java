package utilities;

import org.apache.commons.lang3.RandomStringUtils;

import java.lang.StringBuilder;
import java.util.Random;

public class StringUtils {

    /**
     * @param candidateChars the candidate chars
     * @param length         the number of random chars to be generated
     * @return
     */
    public static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }

    public static String generateRandomString(String format, int length) {
        Random random = new Random();
        String generatedString = null;
        int leftLimit;
        int rightLimit;
        String chars = "~`!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        switch (format) {
            case "alphabetic":
                leftLimit = 97; // letter 'a'
                rightLimit = 122; // letter 'z'
                generatedString = random.ints(leftLimit, rightLimit + 1)
                        .limit(length)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                System.out.println("Generated String: " + generatedString);
                break;
            case "alphaNumeric":
                leftLimit = 48; // numeral '0'
                rightLimit = 122; // letter 'z'
                generatedString = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(length)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

                System.out.println(generatedString);
                break;

            case "numeric":
                leftLimit = 48; // numeral '0'
                rightLimit = 57; // letter '9'
                generatedString = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 48 || i >= 57))
                        .limit(length)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();

                System.out.println(generatedString);
                break;

            case "characters":
                generatedString = RandomStringUtils.random(length, chars);
                break;

            default:
                break;
        }

        return generatedString;
    }
}