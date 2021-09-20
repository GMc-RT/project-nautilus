package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.pages.BadPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BadPageSteps {

  @Autowired
  public BadPage thisPage;
  public String pageName = "BadPage";

  @Given("^I open BadPage$")
  public void iOpenBadPage() {
    log.info("Go to: " + pageName);
    System.out.print("****************************Start " + pageName);
  }

  @Then("^On BadPage I see the title header is \"(.*)\"$")
  public void iSeeMessageBadPage(String message) {
    assertThat(this.thisPage.getHeaderText(), is(message));
  }

  @Then("^On BadPage I see that none of the links on this page are broken$")
  public void iCheckAllLinksBadPage(){
    List<WebElement> allLinks = this.thisPage.getAllLinks();
    String resultString = checkIfAnyLinkFails(allLinks);
    
    if (resultString.equals("There are no broken links on this page")){
      log.info(resultString);
    }else{
        log.error(resultString);
    }
    assertThat(resultString, is("There are no broken links on this page"));
  }

  @Then("^On BadPage I check the response code is \"(.*)\"$")
  public void iCheckResponseCodeBadPage(String msg) throws MalformedURLException, IOException{
    System.out.print("****************************Starting Outer Check Response Codes");
    getResponseCode("https://www.w3.org/standards/badpage");
    HttpURLConnection.setFollowRedirects(false);
    HttpURLConnection con = (HttpURLConnection) new URL("https://www.w3.org/standards/badpage").openConnection();
    con.setRequestMethod("HEAD");
    int response = con.getResponseCode();
    System.out.println("The response is: "+ response + ", expected is : " + msg);
    int expected = Integer.parseInt(msg);
    if (response == expected){
      log.info("The codes are a match("+ msg + "))");
    }else{
      log.error("The codes do not match. Expected : " + msg + " found " + response);
    }
    assertThat(response, is(expected));
  }

  @Then("^On BadPage I see that there are no errors in the console log$")
  public void iCheckLogsForErrorsBadPage(){
    System.out.print("****************************Starting Outer Check logs");
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
      try{
        URL = link.getAttribute("href");
        responseCode = getResponseCode(URL);
        if (responseCode >= 400 || link.getText().contains("@")){
          foundErrors += ("/n Got a code :" + responseCode.toString()+ " from the link with text: " + link.getText());
        }
      }catch( Exception ex){
        foundErrors += ("/n Failed to get response from " + link.getText());

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

  public List<LogEntry> getLogs(){	
		System.out.print("****************************Starting Get logs");
    
    List<LogEntry> newLogs = new ArrayList<LogEntry>();
    
    try{	
      newLogs = thisPage.driver.manage().logs().get(LogType.BROWSER).getAll();
      System.out.print("****************************Log contents/n" 
      + newLogs+ "/n ****************************end Log contents/n");
    } catch (Exception ex){
      System.out.print("Get logs Error: " + ex.toString());
    }
    return newLogs;
	}

	public String getErrorLogReport(){
		System.out.print("****************************Starting Check logs");
		String errorString = "";
    List<LogEntry> newLogs = getLogs();
    for (LogEntry entry : newLogs) {
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
    	JavascriptExecutor js = (JavascriptExecutor) thisPage.driver;  
		js.executeScript(script);
	}

}
