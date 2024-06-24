package org.island.exceptions;

public class OrganismNotFound extends RuntimeException {

    public OrganismNotFound(){}

    public OrganismNotFound(String message){
        super(message);
    }

    public OrganismNotFound(String message, Throwable cause){
        super(message, cause);
    }

    public OrganismNotFound(Throwable cause){
        super(cause);
    }
}
