package com.ebay.ebayTest;

import cucumber.api.java.After;
import cucumber.api.java8.En;
import org.testng.Assert;

import java.util.Objects;

import static com.ebay.ebayTest.EbayTestLogic.getItemFromDropDownMenu;
import static com.ebay.ebayTest.EbayTestLogic.getTextFromClassElement;
import static com.ebay.ebayTest.EbayTestLogic.openWebPage;

/**
 * Created by santi on 4/22/2018.
 */
public class EbayTestDefinition implements En {

    public EbayTestDefinition() {
        Given("^I enter to ebay shop$", () -> {
            try {
                openWebPage("https://www.ebay.com/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Then("^The number of results should be printed$", () -> {
            String count = getTextFromClassElement("rcnt");
            Assert.assertTrue(!Objects.equals(count, ""));
            System.out.println("For this filter the result is: " + count);
        });
        And("^I select the product (.*)$", EbayTestLogic::selectForShoesItem);

        And("^I select the (.+) brand and size (\\d+)$", EbayTestLogic::filterProductByBrandAndSize);

        When("^I click to sort the result by price ascending$", () -> {
            getItemFromDropDownMenu(3);
        });

        Then("^The results should be sorted in ascending$", () -> {
            //This step is only to keep the scenario structure
        });

        Then("^I assert the order taking the first (\\d+) results$", EbayTestLogic::assertOrderByAQuantityOfProducts);

        Then("^The first (\\d+) products with their prices should be printed in console$", EbayTestLogic::printElementsNameAndPrice);

        When("^I Order the products result by name ascending$", EbayTestLogic::printItemsSorted);

        When("^I Order the products result by price descendant$", () -> {
            getItemFromDropDownMenu(4);
        });

        Then("^The products should be printed$", EbayTestLogic::printAllItems);
    }

    @After
    public void tearDown() {
        EbayTestLogic.localDriver.close();
        EbayTestLogic.localDriver.quit();
    }
}
