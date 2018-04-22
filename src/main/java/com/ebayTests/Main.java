package com.ebayTests;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        format = {"pretty", "json:target/cucumber.json", "html:target/html"},
        features = {"src/main/resources"},
        monochrome = true,
        glue = "com.ebayTests"
//        tags = {"@myTests"}
)
public class Main extends AbstractTestNGCucumberTests{
}