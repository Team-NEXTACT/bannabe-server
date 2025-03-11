package site.bannabe.server.domain.payments.service;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.payments.controller.response.PaymentInitializeResponse;
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.domain.rentals.entity.RentalItemTypes;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.global.utils.RandomCodeGenerator;

@Service
@RequiredArgsConstructor
public class PaymentViewService {

  private static final String CURRENCY = "KRW";

  private final RentalItemRepository rentalItemRepository;
  private final OrderInfoService orderInfoService;

  @Value("${bannabe.payment.api-key}")
  private String apiKey;

  @Transactional(readOnly = true)
  public PaymentInitializeResponse getPaymentRequest(String rentalItemToken, Integer rentalTime, PaymentType paymentType) {
    RentalItems rentalItem = rentalItemRepository.findByToken(rentalItemToken);
    RentalItemTypes rentalItemType = rentalItem.getRentalItemType();
    String orderName = createOrderName(rentalItemType.getName(), rentalTime);
    String customerKey = UUID.randomUUID().toString();
    String orderId = RandomCodeGenerator.generateOrderId(LocalDateTime.now());
    Integer amount = rentalItemType.getPrice() * rentalTime;
    orderInfoService.saveOrderInfo(orderId, rentalItemToken, amount, paymentType);
    return new PaymentInitializeResponse(apiKey, amount, CURRENCY, customerKey, orderId, orderName);
  }

  private String createOrderName(String itemName, Integer rentalTime) {
    return itemName + "/" + rentalTime + "시간";
  }

}