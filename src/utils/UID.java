package utils;

import java.util.Base64;
import java.util.UUID; // 32 chars

public class UID {
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

