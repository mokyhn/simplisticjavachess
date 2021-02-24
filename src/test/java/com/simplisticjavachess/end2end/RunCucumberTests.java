package com.simplisticjavachess.end2end;

import com.simplisticjavachess.End2EndTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@End2EndTest
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"})
public class RunCucumberTests {
}
