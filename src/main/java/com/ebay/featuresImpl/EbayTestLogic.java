package com.ebay.featuresImpl;

import com.ebay.browserHelper.AutomationExceptions;
import com.ebay.browserHelper.BrowserHelper;
import com.ebay.browserHelper.Driver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by santi on 4/22/2018.
 */
public class EbayTestLogic {

    static WebDriver localDriver;
    private static Logger logger = Logger.getLogger(EbayTestLogic.class);
    private static Scenario myScenario;

    /**
     * Open a page using selenium web driver
     *
     * @param page A valid URL to navigate
     * @throws Exception
     */
    static void openWebPage(String page) throws Exception {
        BrowserHelper browserHelper = new BrowserHelper(Driver.CHROME);
        localDriver = browserHelper.getLocalDriver();
        localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        localDriver.manage().window().maximize();
        localDriver.get(page);
    }


    /**
     * used to set the Scenario
     *
     * @param myScenarioP the Scenario to be configured
     */
    public static void setMyScenario(Scenario myScenarioP) {
        myScenario = myScenarioP;
    }

    /**
     * Search a product from the search bar
     *
     * @param product String with the product you want to search
     */
    static void selectForShoesItem(String product) {
        WebElement shoes = localDriver.findElement(By.id("gh-ac"));
        WebElement searchButton = localDriver.findElement(By.id("gh-btn"));
        shoes.sendKeys(product);
        searchButton.click();
    }

    /**
     * Make a filter using some key words
     *
     * @param brand String brand to filter
     * @param size  int size to filter
     */
    static void filterProductByBrandAndSize(String brand, Integer size) {
        WebElement brandFilter = localDriver.findElement(By.xpath("//span[text()='" + brand + "']"));
        brandFilter.click();
        WebElement sizeFilter = localDriver.findElement(By.xpath("//span[text()='" + size + "']"));
        sizeFilter.click();
    }


    /**
     * Get a String text from an element Id
     *
     * @param elementId a valid element Id to get it's text value
     * @return
     */
    static String getTextFromClassElement(String elementId) {
        WebElement textElement = localDriver.findElement(By.className(elementId));
        return textElement.getText();
    }

    /**
     * Allow to get an item from menu
     *
     * @param index in index where the item is set
     */
    static void getItemFromDropDownMenu(int index) {
        WebElement webElement = localDriver.findElement(By.className("sort-menu-container"));
        webElement.click();
        webElement.findElement(By.xpath("//*[@id=\"SortMenu\"]/li[" + index + "]/a")).click();
    }

    /**
     * Allow to order products
     *
     * @param productToOrder is the Web element that contains all the product information
     */
    private static void orderProducts(WebElement productToOrder) {
        productToOrder.click();
        if (checkElementPresence(localDriver, By.id("msku-sel-1"))) {
            Select size = new Select(localDriver.findElement(By.id("msku-sel-1")));
            size.selectByVisibleText("10");
        }
        localDriver.findElement(By.id("isCartBtn_btn")).click();
        localDriver.findElement(By.id("contShoppingBtn")).click();
    }


    /**
     * Add products to cart
     *
     * @param quantity number of products to add
     */
    static void assertOrderByAQuantityOfProducts(Integer quantity) {
        for (int c = 0; c < quantity; c++) {
            List<WebElement> productsElement = localDriver.findElements(By.className("imgWr"));
            orderProducts(productsElement.get(c));
        }
        WebElement webElement = localDriver.findElement(By.id("gh-cart-n"));
        Assert.assertTrue(webElement.getText().equals("5"));
    }


    static void printElementsNameAndPrice(Integer quantity) {
        List<WebElement> nameElements = localDriver.findElements(By.className("gvtitle"));
        List<WebElement> priceElements = localDriver.findElements(By.className("gvprices"));
        for (int c = 0; c < quantity; c++) {
            String prName = nameElements.get(c).getText();
            String prPrice = priceElements.get(c).findElement(By.className(" bold")).getText();
//            System.out.println("Product Name: " + prName + "\n" + "Product Price: " + prPrice + "\n");
            logger.info("Product Name: " + prName + "\n" + "Product Price: " + prPrice + "\n");
        }
    }

    static void printItemsSorted() {
        List<WebElement> nameElements = localDriver.findElements(By.className("gvtitle"));
        List<String> names = new ArrayList<>();
        for (WebElement el : nameElements) {
            names.add(el.getText());
        }
        Collections.sort(names);
//        names.forEach(System.out::println);
        for (String name : names) {
            logger.info(name);
        }
    }

    static void printAllItems() {
        List<WebElement> nameElements = localDriver.findElements(By.className("gvtitle"));
        List<WebElement> priceElements = localDriver.findElements(By.className("gvprices"));
        for (int c = 0; c < nameElements.size(); c++) {
            String prName = nameElements.get(c).getText();
            String prPrice = priceElements.get(c).findElement(By.className(" bold")).getText();
//            System.out.println("Product Name: " + prName + "\n" + "Product Price: " + prPrice + "\n");
            logger.info("Product Name: " + prName + "\n" + "Product Price: " + prPrice + "\n");
        }
    }


    private static boolean checkElementPresence(final WebDriver driver, final By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException ignored) {
            return false;
        } catch (StaleElementReferenceException ignored) {
            return false;
        }
    }

    /**
     * Allows to take an screenshot from the screen
     *
     * @throws AutomationExceptions.TakeScreenShotException Execute an exception when there is a problem while taking a screenshot.
     */
    public static void takeScreenShot() throws AutomationExceptions.TakeScreenShotException {

        Robot r = null;
        try {
            r = new Robot();
        } catch (AWTException e) {
            logger.error(e.getMessage());
        }
        java.awt.Rectangle screenRect = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = null;
        if (r != null) {
            screenFullImage = r.createScreenCapture(screenRect);
        }
        ByteArrayOutputStream imageByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            if (screenFullImage != null) {
                ImageIO.write(screenFullImage, "jpg", imageByteArrayOutputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new AutomationExceptions.TakeScreenShotException();
        }
        byte[] imageBytes = imageByteArrayOutputStream.toByteArray();
        myScenario.embed(imageBytes, "image/png");
    }

    /**
     * Validates if the scenario has failed and takes a screenshot
     *
     * @param scenario the scenario to embed the screenshot
     * @throws AutomationExceptions.TakeScreenShotException Execute an exception when there is a problem while taking a screenshot.
     */
    public static void validateScenarioAndTakeScreenShot(Scenario scenario) throws AutomationExceptions.TakeScreenShotException {
        if (scenario.isFailed()) {
            takeScreenShot();
        }
    }
}
