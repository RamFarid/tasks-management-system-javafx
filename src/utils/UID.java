package utils;

import java.util.Base64;
import java.util.UUID; // 32 chars

public class UID {
    private static final int RAW_BYTES = 16;
    private static final int ENCODED_LENGTH = 22;
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
    
    public static boolean isValid(String uid) {
        if (uid == null || uid.length() != ENCODED_LENGTH) {
            return false;
        }

        try {
            byte[] decoded = Base64.getUrlDecoder().decode(uid);
            return decoded.length == RAW_BYTES;
        } catch (IllegalArgumentException e) {
            return false; // invalid base64
        }
    }
}

