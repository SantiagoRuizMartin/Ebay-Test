package com.ebay.browserHelper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;

/**
 * Created by santi on 4/22/2018.
 */
public class BrowserHelper {

    private WebDriver localDriver = null;


    /**
     * Set the private attribute localDriver to a new WebDriver,
     * based on the Operating System and the parameter given.
     *
     * @param browser type of local driver to use.
     *                Valid values are: Driver.CHROME, Driver.FIREFOX or Driver.INTERNET_EXPLORER.
     */
    public BrowserHelper(Driver browser) throws Exception {
        switch (browser) {
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                FirefoxOptions options = new FirefoxOptions();
                capabilities.setCapability("moz:firefoxOptions", options);
                capabilities.setCapability("marionette", true);
                this.localDriver = new FirefoxDriver(capabilities);

                break;

            case CHROME:

                System.setProperty("webdriver.chrome.driver",
                        "src/main/resources/drivers/chromedriver.exe");
                ChromeOptions options2 = new ChromeOptions();
                options2.addArguments("disable-infobars");
                this.localDriver = new ChromeDriver(options2);
                break;
            case SAFARI:
                this.localDriver = new SafariDriver();
                break;
            case IE:
                File file = new File("src/main/resources/drivers/IEDriverServer.exe");
                System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                capabilities.setCapability("initialBrowserUrl", "https://");
                this.localDriver = new InternetExplorerDriver(capabilities);
                break;
            default:
                throw new AutomationExceptions();
        }
    }


    /**
     * Returns the localDriver if it has been initialized with initLocalDriver().
     * Else returns null.
     */
    public WebDriver getLocalDriver() {
        return this.localDriver;
    }

}
