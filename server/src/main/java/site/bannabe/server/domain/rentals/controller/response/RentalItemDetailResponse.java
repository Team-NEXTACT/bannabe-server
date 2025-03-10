package site.bannabe.server.domain.rentals.controller.response;

public record RentalItemDetailResponse(
    String name,
    String image,
    String category,
    String description,
    Integer price,
    Integer stock
) {

}