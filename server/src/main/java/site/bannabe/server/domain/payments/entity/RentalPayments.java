package site.bannabe.server.domain.payments.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.domain.rentals.entity.RentalHistory;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalPayments extends BaseEntity {

  private PaymentType paymentType;

  private PaymentStatus status;

  private PaymentMethod paymentMethod;

  private LocalDateTime paymentDate;

  private Integer totalAmount;

  private String orderId;

  private String paymentKey;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_history_id")
  private RentalHistory rentalHistory;

}