package com.ebay.ebayTest;

import com.ebay.browserHelper.BrowserHelper;
import com.ebay.browserHelper.Driver;
import cucumber.api.java.After;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by santi on 4/22/2018.
 */
public class EbayTestLogic {
    public static WebDriver localDriver;

    static void openWebPage(String page) throws Exception {
        BrowserHelper browserHelper = new BrowserHelper(Driver.CHROME);
        localDriver = browserHelper.getLocalDriver();
        localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        localDriver.manage().window().maximize();
        localDriver.get(page);
    }

    static void selectForShoesItem(String product) {
        WebElement shoes = localDriver.findElement(By.id("gh-ac"));
        WebElement searchButton = localDriver.findElement(By.id("gh-btn"));
        shoes.sendKeys(product);
        searchButton.click();
    }


    static void filterProductByBrandAndSize(String brand, Integer size) {
        WebElement brandFilter = localDriver.findElement(By.xpath("//span[text()='" + brand + "']"));
        brandFilter.click();
        WebElement sizeFilter = localDriver.findElement(By.xpath("//span[text()='" + size + "']"));
        sizeFilter.click();
    }

    static String getTextFromClassElement(String elementId) {
        WebElement textElement = localDriver.findElement(By.className(elementId));
        return textElement.getText();
    }

    static void getItemFromDropDownMenu() {
        WebElement webElement = localDriver.findElement(By.className("sort-menu-container"));
        webElement.click();
        webElement.findElement(By.xpath("//*[@id=\"SortMenu\"]/li[3]/a")).click();
    }

    private static void orderProducts(WebElement webElement) {
        webElement.click();
        if (checkElementPresence(localDriver, By.id("msku-sel-1"))) {
            Select size = new Select(localDriver.findElement(By.id("msku-sel-1")));
            size.selectByVisibleText("10");
        }
        localDriver.findElement(By.id("isCartBtn_btn")).click();
        localDriver.findElement(By.id("contShoppingBtn")).click();
    }

    static void assertOrderByFirstProducts(Integer quantity) {
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
            System.out.println("Product Name: " + prName + "\n" + "Product Price: " + prPrice);
        }
    }

    static void printItemsSorted() {
        List<WebElement> nameElements = localDriver.findElements(By.className("gvtitle"));
        List<String> names = new ArrayList<>();
        for (WebElement el : nameElements) {
            names.add(el.getText());
        }
        Collections.sort(names);
        names.forEach(System.out::println);

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
}
