package jpabook.jpashop.exception;

public class NewPasswordNotMatchedException extends  RuntimeException{

    public NewPasswordNotMatchedException() {
        super();
    }

    public NewPasswordNotMatchedException(String message) {
        super(message);
    }

    public NewPasswordNotMatchedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewPasswordNotMatchedException(Throwable cause) {
        super(cause);
    }

}
