package site.bannabe.server.domain.payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.bannabe.server.domain.payments.controller.request.PaymentCalculateRequest;
import site.bannabe.server.domain.payments.controller.response.PaymentCalculateResponse;
import site.bannabe.server.domain.payments.controller.response.PaymentCheckoutUrlResponse;
import site.bannabe.server.domain.payments.service.PaymentService;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  // 결제금액이 변조되지 않도록 서버에서 계산해서 내려주는 요청
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/calculate")
  public PaymentCalculateResponse calculateAmount(@RequestBody PaymentCalculateRequest paymentRequest) {
    return paymentService.calculateAmount(paymentRequest);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping("/checkout-url")
  public PaymentCheckoutUrlResponse getCheckoutUrl() {
    return new PaymentCheckoutUrlResponse("http://localhost:8080/v1/payments/checkout");
  }

}