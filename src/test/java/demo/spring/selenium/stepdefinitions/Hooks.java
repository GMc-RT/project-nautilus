package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.SpringContextConfiguration;
import demo.spring.selenium.config.ProjectNautilusProperties;
import demo.spring.selenium.pages.BadPage;
import demo.spring.selenium.pages.HtmlCss;
import demo.spring.selenium.pages.Multimodal;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {SpringContextConfiguration.class})
@Slf4j
public class Hooks {

  @Autowired public ProjectNautilusProperties properties;
  @Autowired public WebDriver driver;
  @Autowired public BadPage badpage;
  @Autowired public HtmlCss htmlCss;
  @Autowired public Multimodal multimodal;

  @Before
  public void logScenarioName(Scenario scenario) {    
    log.info("[STARTED] Scenario: " + scenario.getName());
	if (scenario.getName().toUpperCase().contains("BADPAGE")){
		openBadPage();
	} else if (scenario.getName().toUpperCase().contains("MULTIMODAL")){
		openMultimodalURL();
	} else if (scenario.getName().toUpperCase().contains("HTMLCSS")){
		openHtmlCssURL();
	} else{

	}
  }

//   @AfterTest
//   public void closeBrowser(Scenario scenario) {
// 	try{
//     	// byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    		// scenario.attach(screenshot, "image/png", scenario.getName());
// 		driver.quit();
// 		driver = null;
	
// 	}catch(Exception ex){
// 		log.info("Driver Already closed");
// 	}
//     log.info("[ENDED] Scenario: " + scenario.getName());
//   }

  @After
  public void quitBrowser(Scenario scenario) {
	try{
    	// byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
   		// scenario.attach(screenshot, "image/png", scenario.getName());
		driver.quit();
		driver = null;
	
	}catch(Exception ex){
		log.info("Driver Already closed");
	}
    log.info("[Quit] Scenario: " + scenario.getName());
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

