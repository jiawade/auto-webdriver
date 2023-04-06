package io.driver.exception;

public class NotFoundDriverAddressException extends AutoDriverException {
    public NotFoundDriverAddressException() {
    }

    public NotFoundDriverAddressException(String message) {
        super(message);
    }
}
