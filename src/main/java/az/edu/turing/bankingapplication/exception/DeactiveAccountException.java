package az.edu.turing.bankingapplication.exception;

public class DeactiveAccountException extends RuntimeException {

    public DeactiveAccountException(String message){
        super(message);
    }
}
