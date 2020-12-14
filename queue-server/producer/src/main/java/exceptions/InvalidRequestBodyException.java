package exceptions;

public class InvalidRequestBodyException extends Exception {
  public InvalidRequestBodyException(String msg) {
    super(msg);
  }
}
