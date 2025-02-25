package site.bannabe.server.domain.rentals.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.bannabe.server.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalItemTypes extends BaseEntity {

  private String category; // enum으로 변경해야함! (카테고리)

  private String name;

  private String image;

  private String description;

  private Integer price;

}