package site.bannabe.server.global.utils;

import java.security.SecureRandom;

public class RandomCodeGenerator {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String NICKNAME_PREFIX = "Bannabe_";
  private static final int NICKNAME_LENGTH = 12;
  private static final int AUTH_CODE_LENGTH = 6;

  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateRandomNickname() {
    return generateRandomString(NICKNAME_PREFIX, NICKNAME_LENGTH);
  }

  public static String generateAuthCode() {
    return generateRandomString("", AUTH_CODE_LENGTH);
  }

  private static String generateRandomString(String prefix, int length) {
    StringBuilder sb = new StringBuilder(prefix);
    for (int i = 0; i < length; i++) {
      sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }

}