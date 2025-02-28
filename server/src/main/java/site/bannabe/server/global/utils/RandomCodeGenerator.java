package site.bannabe.server.global.utils;

import java.security.SecureRandom;

public class RandomCodeGenerator {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String PREFIX = "Bannabe_";
  private static final int NICKNAME_LENGTH = 12;
  private static final int AUTH_CODE_LENGTH = 6;

  private static final SecureRandom RANDOM = new SecureRandom();

  public static String generateRandomNickname() {
    StringBuilder sb = new StringBuilder(PREFIX);
    for (int i = 0; i < NICKNAME_LENGTH; i++) {
      sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }

  public static String generateAuthCode() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
      sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
    }
    return sb.toString();
  }

}