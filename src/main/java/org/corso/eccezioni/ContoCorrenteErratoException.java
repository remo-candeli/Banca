package org.corso.eccezioni;

public class ContoCorrenteErratoException extends Throwable {
    public ContoCorrenteErratoException() {
    }

    public ContoCorrenteErratoException(String message) {
        super(message);
    }
}
