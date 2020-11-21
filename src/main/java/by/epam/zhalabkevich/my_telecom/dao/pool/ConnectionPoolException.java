package by.epam.zhalabkevich.my_telecom.dao.pool;

public class ConnectionPoolException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
    }

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Exception e) {
        super(e);
    }


}