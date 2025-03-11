package site.bannabe.server.domain.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentTestController {

  @GetMapping("/payment-test")
  public String test() {
    return "test";
  }

}