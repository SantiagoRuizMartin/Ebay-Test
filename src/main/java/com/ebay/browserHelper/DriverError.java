package com.ebay.browserHelper;

/**
 * Created by santi on 4/22/2018.
 */
public class DriverError extends Exception{

    private static final long serialVersionUID = 1997753363232807009L;


    public DriverError(){}

    public DriverError(String message){
        super(message);
    }

    public DriverError(Throwable cause){
        super(cause);
    }

    public DriverError(String message, Throwable cause){
        super(message,cause);
    }

    public DriverError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }


}

