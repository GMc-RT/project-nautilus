package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.SpringContextConfiguration;
import demo.spring.selenium.config.ProjectNautilusProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = {SpringContextConfiguration.class})
@Slf4j
public class Hooks {

  @Autowired private ProjectNautilusProperties properties;
  @Autowired private WebDriver driver;
  @Autowired private static LogEntries logs;

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
    // byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    // scenario.attach(screenshot, "image/png", scenario.getName());
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

	public LogEntries getLogs(){		
		logs= driver.manage().logs().get(LogType.BROWSER);	
		clearLogs();
    return logs;
	}

	public String getErrorLogReport(){
		String errorString = "";
		if (logs == null || logs.getAll().isEmpty())
			return "No Error logs found";
		else{
			for (LogEntry entry : logs) {
					if (entry.getLevel().equals(Level.SEVERE)){
						errorString += errorString + "/n SEVERE: " + entry.toString();
					}
				}
			return "The following logs were found: ";
		}
	}
	
	public  void clearLogs(){
		String script = "console.clear();";
    	JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript(script);
	}

	public  int getResponseCode(String URLName){
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return con.getResponseCode();
		}
		catch (Exception e) {
			log.info("The link " + URLName + " does not return a valid request");
			return 901;
		}
	}

	public String checkIfAnyLinkFails(List<WebElement> allLinks){
		String successMsg = "There are no broken links on this page";
		String failureMsg = "Here are the broken links from this page: ";
		String URL = "";
		Integer responseCode;
		String foundErrors = "";

		for (WebElement link : allLinks){
			URL = link.getAttribute("href");
			responseCode = getResponseCode(URL);
			if (responseCode >= 400){
				foundErrors += ("/n Got a code :" + responseCode.toString()+ " from the link with text: " + link.getText());
			}
		}
		if (foundErrors.length() == 0){
			return successMsg;
		}else{
			return failureMsg + foundErrors;
		}

	}


}
