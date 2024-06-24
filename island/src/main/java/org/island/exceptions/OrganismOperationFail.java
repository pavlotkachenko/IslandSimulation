package org.island.exceptions;

@SuppressWarnings("unused")
public class OrganismOperationFail extends RuntimeException{

    public OrganismOperationFail(){}

    public OrganismOperationFail(String message){
        super(message);
    }

    public OrganismOperationFail(String message, Throwable cause){
        super(message, cause);
    }

    public OrganismOperationFail(Throwable cause){
        super(cause);
    }
}
