package school.hei.restaurant.service.exception;

public class ClientException extends RuntimeException {
    public ClientException(Exception e) {
        super(e);
    }
    public ClientException(String message) {
        super(message);
    }
}
