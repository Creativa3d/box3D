package utiles;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class AException extends Exception {
    static final long serialVersionUID = 1L;
    private Throwable cause = this;

    public AException() {
	super();
    }
    public AException(String message) {
	super(message);
    }
    public AException(String message, Throwable cause) {
        super(message);
        this.cause=cause;
    }

    public synchronized Throwable initCause(Throwable cause) {
        if (this.cause != this)
            throw new IllegalStateException("Can't overwrite cause");
        if (cause == this)
            throw new IllegalArgumentException("Self-causation not permitted");
        this.cause = cause;
        return this;
    }

    public Throwable getCause() {
        return (cause==this ? null : cause);
    }

    public static String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder buf = new StringBuilder();
            if (message != null && !message.equals("")) {
                buf.append(message).append(": ");
            }
            buf.append("lanzada por exception ").append(cause.toString());
            return buf.toString();
        } else {
            return message;
        }
    }

    /**
     * Return the detail message, including the message from the nested exception
     * if there is one.
     */
    public String getMessage() {
        return buildMessage(super.getMessage(), getCause());
    }
}
