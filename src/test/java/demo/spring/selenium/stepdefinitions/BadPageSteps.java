package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.config.ProjectNautilusProperties;
import demo.spring.selenium.pages.BadPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BadPageSteps {

  @Autowired
  private BadPage badPage;
  private Hooks hooks;
  private ProjectNautilusProperties properties;

  @Given("^I open BadPage$")
  public void iOpenBadPage() {
    log.info("Go to: BadPage");
  }

  @Then("^On BadPage I see the title header is \"(.*)\"$")
  public void iSeeMessageBadPage(String message) {
    assertThat(this.badPage.getHeaderText(), is(message));
  }

  @Then("^On BadPage I see that none of the links on this page are broken$")
  public void iCheckAllLinksBadPage(){
    List<WebElement> allLinks = this.badPage.getAllLinks();
    String resultString = hooks.checkIfAnyLinkFails(allLinks);
    
    if (resultString.equals("There are no broken links on this page")){
      log.info(resultString);
    }else{
        log.error(resultString);
    }
    assertThat("There are no broken links on this page", is(resultString));
  }

  @Then("^On BadPage I check the response code is \"(.*)\"$")
  public void iCheckResponseCodeBadPage(String msg){
    int response = hooks.getResponseCode(properties.getBadPageURL());
    int expected = Integer.parseInt(msg);
    if (response == expected){
      log.info("The codes are a match("+ msg + "))");
    }else{
      log.error("The codes do not match. Expected : " + msg + " found " + response);
    }
    assertThat(response, is(msg));
  }

  @Then("^On BadPage I see that there are no errors in the console log$")
  public void iCheckLogsForErrorsBadPage(){
    String result = hooks.getErrorLogReport();
    if (result.equals("No Error logs found")){
      log.info("The Logs have no errors.");
    }else{
      log.error(result);
    }
    hooks.clearLogs();
    assertThat(result, is("No Error logs found"));    
  }

}
