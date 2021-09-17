package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.SpringContextConfiguration;
import demo.spring.selenium.config.ProjectNautilusProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {SpringContextConfiguration.class})
@Slf4j
public class Hooks {

  @Autowired public ProjectNautilusProperties properties;
  @Autowired public WebDriver driver;
  

  @Before
  public void logScenarioName(Scenario scenario) {    
    log.info("[STARTED] Scenario: " + scenario.getName());
	if (scenario.getName().contains("BADPAGE")){
		openBadPage();
	} else if (scenario.getName().contains("MULTIMODAL")){
		openMultimodalURL();
	} else if (scenario.getName().contains("HTMLCSS")){
		openHtmlCssURL();
	} else{

	}
  }

  @After
  public void closeBrowser(Scenario scenario) {
	  //screenshots switched off while debugging
    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    scenario.attach(screenshot, "image/png", scenario.getName());
    driver.quit();
    log.info("[ENDED] Scenario: " + scenario.getName());
  }

  
  	public void openBadPage(){
		driver.get(properties.getBadPageURL());
	  }
	
	public void openMultimodalURL(){
		driver.get(properties.getMultimodalURL());
	}

	public void openHtmlCssURL(){
		driver.get(properties.getHtmlCssURL());
	}

}

