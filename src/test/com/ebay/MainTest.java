package com.ebay;


import cucumber.api.CucumberOptions;


@CucumberOptions(
        format = {"pretty", "json:target/test-classes/cucumber.json", "html:target/html"},
        features = {"src/main/resources"},
        monochrome = true,
        glue = "net.sruiz"
)
public class MainTest {
}