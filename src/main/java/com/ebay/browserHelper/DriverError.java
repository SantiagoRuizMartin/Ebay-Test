package com.ebay.browserHelper;

/**
 * Created by santi on 4/22/2018.
 */
public class DriverError extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    /**
     * Driver error exception
     *
     * @param message Message to show
     */
    public DriverError(String message) {
        super(message);
    }


}

