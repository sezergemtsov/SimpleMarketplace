package testcase.effective_mobile.exceptions;

public class InternalServerError extends RuntimeException {
    public InternalServerError(String msg) {
        super(msg);
    }
}
