package site.bannabe.server.domain.payments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.payments.controller.request.PaymentCalculateRequest;
import site.bannabe.server.domain.payments.controller.response.PaymentCalculateResponse;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final RentalItemRepository rentalItemRepository;

  @Transactional(readOnly = true)
  public PaymentCalculateResponse calculateAmount(PaymentCalculateRequest paymentRequest) {
    String rentalItemToken = paymentRequest.rentalItemToken();
    Integer rentalItemPrice = rentalItemRepository.findRentalItemPrice(rentalItemToken);
    Integer amount = rentalItemPrice * paymentRequest.rentalTime();
    return new PaymentCalculateResponse(rentalItemToken, rentalItemPrice, paymentRequest.rentalTime(), amount);
  }

}