package zw.co.techtrendz.school.management.api.common.exception;

public class YearPassedException extends RuntimeException {

    public YearPassedException(String message) {
        super(message);
    }

    public YearPassedException(String message, Throwable cause) {
        super(message, cause);
    }

    public YearPassedException(Throwable cause) {
        super(cause);
    }
}
