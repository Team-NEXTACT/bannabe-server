package site.bannabe.server.global.utils;

import java.security.SecureRandom;

public class RandomNicknameGenerator {

  private static final String PREFIX = "Bannabe_";

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateRandomNickname() {
    StringBuilder sb = new StringBuilder(PREFIX);
    for (int i = 0; i < 12; i++) {
      sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }

}