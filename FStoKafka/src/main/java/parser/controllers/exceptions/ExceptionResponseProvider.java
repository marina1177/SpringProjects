package parser.controllers.exceptions;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionResponseProvider {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public ResponseEntity<ExceptionResponse> serverException() {
    ExceptionResponse response = new ExceptionResponse();
    response.setData("Internal Server error");
    response.setResponseCode(500);
    response.setStatus("error");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

//  @ExceptionHandler(ConstraintViolationException.class)
//  public void constraintViolationException(HttpServletResponse response) throws IOException {
//    response.sendError(HttpStatus.BAD_REQUEST.value());
//  }
}
