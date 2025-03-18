package site.bannabe.server.domain.payments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.bannabe.server.domain.payments.controller.request.PaymentCalculateRequest;
import site.bannabe.server.domain.payments.controller.request.PaymentConfirmRequest;
import site.bannabe.server.domain.payments.controller.response.PaymentCalculateResponse;
import site.bannabe.server.domain.payments.controller.response.RentalHistoryTokenResponse;
import site.bannabe.server.domain.payments.entity.RentalPayments;
import site.bannabe.server.domain.payments.repository.RentalPaymentRepository;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.domain.rentals.entity.RentalItems;
import site.bannabe.server.domain.rentals.repository.RentalItemRepository;
import site.bannabe.server.domain.rentals.service.StockLockService;
import site.bannabe.server.domain.users.entity.Users;
import site.bannabe.server.domain.users.repository.UserRepository;
import site.bannabe.server.global.api.TossPaymentApiClient;
import site.bannabe.server.global.api.TossPaymentConfirmResponse;
import site.bannabe.server.global.exceptions.BannabeServiceException;
import site.bannabe.server.global.exceptions.ErrorCode;
import site.bannabe.server.global.type.OrderInfo;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final RentalItemRepository rentalItemRepository;
  private final RentalPaymentRepository rentalPaymentRepository;
  private final UserRepository userRepository;
  private final TossPaymentApiClient tossApiClient;
  private final OrderInfoService orderInfoService;
  private final StockLockService stockLockService;

  @Transactional(readOnly = true)
  public PaymentCalculateResponse calculateAmount(PaymentCalculateRequest paymentRequest) {
    String rentalItemToken = paymentRequest.rentalItemToken();
    Integer rentalItemPrice = rentalItemRepository.findRentalItemPrice(rentalItemToken);
    Integer amount = rentalItemPrice * paymentRequest.rentalTime();
    return new PaymentCalculateResponse(rentalItemToken, rentalItemPrice, paymentRequest.rentalTime(), amount);
  }

  @Transactional
  public RentalHistoryTokenResponse confirmPayment(String entityToken, PaymentConfirmRequest paymentConfirmRequest) {
    OrderInfo orderInfo = getOrderInfoAndValidateAmount(paymentConfirmRequest);
    TossPaymentConfirmResponse paymentConfirmResponse = tossApiClient.confirmPaymentRequest(paymentConfirmRequest);
    RentalItems rentalItem = rentalItemRepository.findByToken(orderInfo.getRentalItemToken());
    RentalHistory rentalHistory = createRentalHistory(entityToken, orderInfo, paymentConfirmResponse, rentalItem);
    saveRentalPaymentsAndRentalHistory(paymentConfirmResponse, orderInfo, rentalHistory);
    processRental(paymentConfirmRequest, rentalItem);
    return new RentalHistoryTokenResponse(rentalHistory.getToken());
  }

  private OrderInfo getOrderInfoAndValidateAmount(PaymentConfirmRequest paymentConfirmRequest) {
    OrderInfo orderInfo = orderInfoService.findOrderInfoBy(paymentConfirmRequest.orderId());
    if (!orderInfo.getAmount().equals(paymentConfirmRequest.amount())) {
      throw new BannabeServiceException(ErrorCode.AMOUNT_MISMATCH);
    }
    return orderInfo;
  }

  private RentalHistory createRentalHistory(String entityToken, OrderInfo orderInfo,
      TossPaymentConfirmResponse paymentConfirmResponse, RentalItems rentalItem) {
    Users user = userRepository.findByToken(entityToken);
    return RentalHistory.create(rentalItem, user, orderInfo, paymentConfirmResponse.approvedAt());
  }

  private void saveRentalPaymentsAndRentalHistory(TossPaymentConfirmResponse paymentConfirmResponse, OrderInfo orderInfo,
      RentalHistory rentalHistory) {
    RentalPayments rentalPayments = RentalPayments.create(paymentConfirmResponse, orderInfo, rentalHistory);
    rentalPaymentRepository.save(rentalPayments);
  }

  private void processRental(PaymentConfirmRequest paymentConfirmRequest, RentalItems rentalItem) {
    stockLockService.decreaseStock(rentalItem);
    rentalItem.rentOut();
    orderInfoService.removeOrderInfo(paymentConfirmRequest.orderId());
  }

}