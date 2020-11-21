package parser.controllers.exceptions;


import lombok.Data;

@Data
public class CounterDto {

  private String message;
  private  Integer counter;
  private long timestamp;

}
