package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.config.ProjectNautilusProperties;
import demo.spring.selenium.pages.Multimodal;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MultimodalSteps {

  @Autowired
  private Multimodal multimodal;
  public LogEntries logs;
  private ProjectNautilusProperties properties;

  @Given("^I open Multimodal$")
  public void iOpenHtmlMultimodal() {
    log.info("Go to: Multimodal");
    }

  @Then("^On Multimodal I see the title header is \"(.*)\"$")
  public void iSeeMessageMultimodal(String message) {
    assertThat(this.multimodal.getHeaderText(), is(message));
  }

  @Then("^On Multimodal I see that none of the links on this page are broken$")
  public void iCheckAllLinksMultimodal(){
    List<WebElement> allLinks = this.multimodal.getAllLinks();
    String resultString = checkIfAnyLinkFails(allLinks);
    
    if (resultString.equals("There are no broken links on this page")){
      log.info(resultString);
    }else{
        log.error(resultString);
    }
    assertThat("There are no broken links on this page", is(resultString));
  }

  @Then("^On Multimodal I check the response code is \"(.*)\"$")
  public void iCheckResponseCodeMultimodal(int msg){
    int response = getResponseCode(properties.getMultimodalURL());
    if (response == msg){
      log.info("The codes are a match("+ msg + "))");
    }else{
      log.error("The codes do not match. Expected : " + msg + " found " + response);
    }
    assertThat(response, is(msg));
  }

  @Then("^On Multimodal I see that there are no errors in the console log$")
  public void iCheckLogsForErrorsMultimodal(){
    getLogs();
    String result = getErrorLogReport();
    if (result.equals("No Error logs found")){
      log.info("The Logs have no errors.");
    }else{
      log.error(result);
    }
    clearLogs();
    assertThat(result, is("No Error logs found"));    
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

  public LogEntries getLogs(){	
		System.out.print("****************************Starting Get logs");	
		logs= multimodal.driver.manage().logs().get(LogType.BROWSER);	
		clearLogs();
    return logs;
	}

  public String getErrorLogReport(){
		System.out.print("****************************Starting Check logs");
		String errorString = "";
    for (LogEntry entry : logs) {
					if (entry.getLevel().equals(Level.SEVERE)){
						errorString += errorString + "/n SEVERE: " + entry.toString();
					}
				}			
	  if (errorString == ""){
			return "No Error logs found";
    }
		else{
      return "The following logs were found: " + errorString;
    }
	}
	
	public  void clearLogs(){
		System.out.print("****************************Starting Clear logs");
		String script = "console.clear();";
    	JavascriptExecutor js = (JavascriptExecutor) multimodal.driver;  
		js.executeScript(script);
	}


}


