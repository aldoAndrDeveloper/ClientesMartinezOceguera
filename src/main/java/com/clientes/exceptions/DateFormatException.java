package com.clientes.exceptions;

public class DateFormatException extends Exception{
    public DateFormatException(String fecha){
        super(fecha);
    }

}
