package Exceptions;

public class NotFoundException extends Exception {

    /**
     * Creates a new instance of <code>LocalNotFoundException</code> without
     * detail message.
     */
    public NotFoundException() {
    }

    /**
     * Constructs an instance of <code>LocalNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotFoundException(String msg) {
        super(msg);
    }
}
