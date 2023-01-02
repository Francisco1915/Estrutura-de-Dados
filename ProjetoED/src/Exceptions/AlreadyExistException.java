package Exceptions;

public class AlreadyExistException extends Exception{

    /**
     * Creates a new instance of <code>LocalAlreadyExistException</code> without
     * detail message.
     */
    public AlreadyExistException() {
    }

    /**
     * Constructs an instance of <code>LocalAlreadyExistException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
