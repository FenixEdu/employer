package org.fenixedu.employer.exception;

public class JobFailedException extends Exception {

    private static final long serialVersionUID = -2844257968626248439L;

    public JobFailedException() {
    }

    public JobFailedException(String message) {
        super(message);
    }
}
