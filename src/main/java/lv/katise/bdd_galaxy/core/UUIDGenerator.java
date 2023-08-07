package lv.katise.bdd_galaxy.core;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDGenerator {
    public static UUID generateUUIDFromString(String inputString) {
        // Convert the input string to bytes using UTF-8 encoding
        byte[] bytes = inputString.getBytes(StandardCharsets.UTF_8);

        // Generate a UUID based on the bytes
        return UUID.nameUUIDFromBytes(bytes);
    }
}
