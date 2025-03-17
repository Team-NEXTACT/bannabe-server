package site.bannabe.server.global.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import site.bannabe.server.domain.payments.controller.request.PaymentConfirmRequest;
import site.bannabe.server.global.utils.JsonUtils;

@Component
@RequiredArgsConstructor
public class TossPaymentApiClient {

  private static final String PAYMENT_CONFIRM_URL = "https://api.tosspayments.com/v1/payments/confirm";

  private final RestClient restClient;
  private final JsonUtils jsonUtils;

  @Value("${bannabe.payment.secret-key}")
  private String secretKey;

  public TossPaymentConfirmResponse confirmPaymentRequest(PaymentConfirmRequest paymentConfirmRequest) {
    String body = jsonUtils.serializedObjectToJson(paymentConfirmRequest);
    // 예외 처리 꼭 꼭 해야함 꼭!!
    return restClient.post().uri(PAYMENT_CONFIRM_URL).headers(header -> {
                       header.setBasicAuth(secretKey, "");
                       header.setContentType(MediaType.APPLICATION_JSON);
                     })
                     .body(body)
                     .retrieve()
                     .body(TossPaymentConfirmResponse.class);
  }

}