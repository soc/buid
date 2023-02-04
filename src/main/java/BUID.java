import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

public final class BUID {

  private BUID() {}

  public static String create() {
    byte[] buffer = new byte[15];

    Instant time = Instant.now().minusSeconds(1576800000); // 50 years
    var posixMillis = time.toEpochMilli();
    int posixNanos = time.getNano() << 2 >>> 20 << 2;
    byte posixNanoHigh = (byte) (posixNanos >>> 8);
    byte posixNanosLow = (byte) posixNanos;
    long random = 0L;
    try {
      random = SecureRandom.getInstanceStrong().nextLong();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    buffer[0] = (byte) (posixMillis >>> 34);
    buffer[1] = (byte) (posixMillis >>> 26);
    buffer[2] = (byte) (posixMillis >>> 18);
    buffer[3] = (byte) (posixMillis >>> 10);
    buffer[4] = (byte) (posixMillis >>> 2);
    buffer[5] = (byte) (((byte) (posixMillis >>> 10)) | posixNanoHigh);
    buffer[6] = (byte) (posixNanosLow | (byte) (random >>> 62));
    buffer[7] = (byte) (random >>> 56);
    buffer[8] = (byte) (random >>> 48);
    buffer[9] = (byte) (random >>> 40);
    buffer[10] = (byte) (random >>> 32);
    buffer[11] = (byte) (random >>> 24);
    buffer[12] = (byte) (random >>> 16);
    buffer[13] = (byte) (random >>> 8);
    buffer[14] = (byte) random;

    return Base64.getUrlEncoder().encodeToString(buffer);
  }

  public static void main(String[] args) {
    System.out.println(create().length());
    System.out.println(create());
    System.out.println(create());
    System.out.println(create());
    System.out.println(create());
    System.out.println(create());
  }

}
