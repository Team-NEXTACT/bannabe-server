package site.bannabe.server.global.aws;

import io.awspring.cloud.s3.S3Operations;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3Service {

  private static final String PROFILE_IMAGE_DIRECTORY = "profile-images/";

  private final S3Operations s3Operations;

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucketName;

  public String getPreSignedUrl(String objectKey) {
    objectKey = PROFILE_IMAGE_DIRECTORY + objectKey;
    return s3Operations.createSignedPutURL(bucketName, objectKey, Duration.ofMinutes(5L)).toString();
  }

}