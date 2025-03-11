package site.bannabe.server.domain.payments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.bannabe.server.domain.payments.entity.PaymentType;
import site.bannabe.server.global.redis.OrderInfoClient;
import site.bannabe.server.global.type.OrderInfo;

@Service
@RequiredArgsConstructor
public class OrderInfoService {

  private final OrderInfoClient orderInfoClient;

  public void saveOrderInfo(String orderId, String rentalItemToken, Integer amount, PaymentType paymentType) {
    OrderInfo orderInfo = new OrderInfo(rentalItemToken, amount, paymentType);
    orderInfoClient.save(orderId, orderInfo);
  }

  public OrderInfo getOrderInfo(String orderId) {
    return orderInfoClient.findBy(orderId);
  }

  public void deleteOrderInfo(String orderId) {
    orderInfoClient.deleteBy(orderId);
  }

}