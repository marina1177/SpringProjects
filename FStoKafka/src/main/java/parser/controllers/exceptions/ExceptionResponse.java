package parser.controllers.exceptions;

import lombok.Data;

@Data
public class ExceptionResponse {

  private String data;
  private int responseCode;
  private String status;
}
