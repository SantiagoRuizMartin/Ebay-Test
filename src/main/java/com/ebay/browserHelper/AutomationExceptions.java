package com.ebay.browserHelper;

/**
 * Created by santi on 4/22/2018.
 */
public class AutomationExceptions extends Exception {

    private static final long serialVersionUID = 1997753363232807009L;

    /**
     * Driver error exception
     */
    public static class DriverException extends Exception {
        public DriverException() {
            super("An Exception occurs trying to take the screenshot");
        }
    }


    /**
     * Execute an exception when there is a problem while taking a screenshot.
     */
    public static class TakeScreenShotException extends Exception {
        public TakeScreenShotException() {
            super("An Exception occurs trying to take the screenshot");
        }
    }


}

