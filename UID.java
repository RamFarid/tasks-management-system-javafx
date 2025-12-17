package PLProject;

import java.util.Base64;
import java.util.UUID; // 32 chars

public class UID {
    private String value;

    // Constructor for generating a new UID
    public UID() {
        this.value = generate();
    }

    // Constructor for loading UID from file
    public UID(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }

    // Convert to string when saving
    @Override
    public String toString() {
        return value;
    }

    // Your existing generate() method
    public static String generate() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = toBytes(uuid);

        return Base64.getUrlEncoder()
                     .withoutPadding()
                     .encodeToString(bytes);
    }

    private static byte[] toBytes(UUID uuid) {
        byte[] bytes = new byte[16];

        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();

        for (int i = 0; i < 8; i++)
            bytes[i] = (byte) (msb >>> (8 * (7 - i)));

        for (int i = 8; i < 16; i++)
            bytes[i] = (byte) (lsb >>> (8 * (15 - i)));

        return bytes;
    }
}

