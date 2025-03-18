package site.bannabe.server.domain.rentals.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/returns")
@RequiredArgsConstructor
public class ReturnController {

  @GetMapping("/{rentalItemToken}")
  public void getReturnItemInfo(@PathVariable("rentalItemToken") String rentalItemToken){

  }

}