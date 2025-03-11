package site.bannabe.server.domain.payments.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.bannabe.server.domain.payments.controller.response.PaymentInitializeResponse;
import site.bannabe.server.domain.payments.service.PaymentViewService;

@Controller
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentViewController {

  private final PaymentViewService paymentViewService;

  @GetMapping("/checkout")
  public String paymentRequest(
      @RequestParam("rentalItemToken") String rentalItemToken,
      @RequestParam("rentalTime") Integer rentalTime,
      Model model
  ) {
    PaymentInitializeResponse paymentInitializeResponse = paymentViewService.getPaymentRequest(rentalItemToken, rentalTime);
    model.addAttribute("paymentRequest", paymentInitializeResponse);
    return "payments/checkout";
  }


}