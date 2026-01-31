package com.microservices.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@Schema(name = "Response", description = "Data Transfer Object for Response information")
public class ResponseDto {
  //@Schema(description = "Status code of the response")
  private String statusCode;
  //@Schema(description = "Status message of the response")
  private String statusMessage;
}
