package com.ebay.featuresImpl;

import com.ebay.browserHelper.AutomationExceptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

import static com.ebay.featuresImpl.EbayTestLogic.getItemsSorted;
import static com.ebay.featuresImpl.EbayTestLogic.printFromList;
import static com.ebay.featuresImpl.EbayTestLogic.validateScenarioAndTakeScreenShot;

/**
 * Created by santi on 4/22/2018.
 */
public class EbayTestDefinition implements En {

    private static Logger logger = Logger.getLogger(EbayTestDefinition.class);
    List<String> sortedItems;

    @Before()
    public void embedScreenshotStep(Scenario scenario) {
        EbayTestLogic.setMyScenario(scenario);
    }

    public EbayTestDefinition() {
        Given("^I enter to ebay shop$", () -> {
            try {
                EbayTestLogic.openWebPage("https://www.ebay.com/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Then("^The number of results should be printed$", () -> {
            String count = EbayTestLogic.getTextFromClassElement("rcnt");
            Assert.assertTrue(!Objects.equals(count, ""));
            try {
                EbayTestLogic.takeScreenShot();
            } catch (AutomationExceptions.TakeScreenShotException e) {
                e.printStackTrace();
            }
//            System.out.println("For this filter the result is: " + count);
            logger.info("For this filter the result is: " + count);
        });
        And("^I select the product (.*)$", EbayTestLogic::selectForShoesItem);

        And("^I select the (.+) brand and size (\\d+)$", EbayTestLogic::filterProductByBrandAndSize);

        When("^I click to sort the result by price ascending$", () -> {
            EbayTestLogic.getItemFromDropDownMenu(3);
            try {
                EbayTestLogic.takeScreenShot();
            } catch (AutomationExceptions.TakeScreenShotException e) {
                e.printStackTrace();
            }
        });

        Then("^The results should be sorted in ascending$", () -> {
            //This step is only to keep the scenario structure
        });

        Then("^I assert the order taking the first (\\d+) results$", (Integer quantity) -> {
            EbayTestLogic.theOrderIsCorrect(quantity);
            try {
                EbayTestLogic.takeScreenShot();
            } catch (AutomationExceptions.TakeScreenShotException e) {
                e.printStackTrace();
            }
        });

        Then("^The first (\\d+) products with their prices should be printed in console$", EbayTestLogic::printElementsNameAndPrice);

        When("^I Order the products result by name ascending$", () -> {
            sortedItems = getItemsSorted();
        });

        When("^I Order the products result by price descendant$", () -> {
            EbayTestLogic.getItemFromDropDownMenu(4);
        });

        Then("^The products should be printed$", EbayTestLogic::printAllItems);
        Then("^The products sorted should be printed$", () -> {
            printFromList(sortedItems);
        });
    }


    @After
    public void embedScreenshot(Scenario scenario) throws AutomationExceptions.TakeScreenShotException {
        validateScenarioAndTakeScreenShot(scenario);
        EbayTestLogic.localDriver.quit();
    }


}
