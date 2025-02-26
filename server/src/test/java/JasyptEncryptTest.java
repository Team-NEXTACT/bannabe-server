import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

public class JasyptEncryptTest {

  @Test
  void stringEncryptor() {
    String value = "fill in encryption necessary value";

    System.out.println("encryptValue :: " + jasyptEncoding(value));
  }

  public String jasyptEncoding(String value) {
    String key = System.getenv("JASYPT_PASSWORD");
    if (key == null || key.isEmpty()) {
      throw new IllegalArgumentException("JASYPT_PASSWORD is empty");
    }
    StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
    pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    pbeEnc.setPassword(key);
    pbeEnc.setIvGenerator(new RandomIvGenerator());
    return "ENC(" + pbeEnc.encrypt(value) + ")";
  }

}