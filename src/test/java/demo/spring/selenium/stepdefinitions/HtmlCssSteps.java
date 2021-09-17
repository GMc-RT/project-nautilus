package demo.spring.selenium.stepdefinitions;

import demo.spring.selenium.config.ProjectNautilusProperties;
import demo.spring.selenium.pages.HtmlCss;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HtmlCssSteps {

  @Autowired
  private HtmlCss htmlCss;
  private Hooks hooks;
  private ProjectNautilusProperties properties;

  @Given("^I open HtmlCss$")
  public void iOpenHtmlCss() {
    log.info("Go to: HtmlCss");
    }

  @Then("^On HtmlCss I see the title header is \"(.*)\"$")
  public void iSeeMessageHtml(String message) {
    assertThat(this.htmlCss.getHeaderText(), is(message));
  }

  @Then("^On HtmlCss I see that none of the links on this page are broken$")
  public void iCheckAllLinksHtml(){
    List<WebElement> allLinks = this.htmlCss.getAllLinks();
    String resultString = hooks.checkIfAnyLinkFails(allLinks);
    
    if (resultString.equals("There are no broken links on this page")){
      log.info(resultString);
    }else{
        log.error(resultString);
    }
    assertThat("There are no broken links on this page", is(resultString));
  }

  @Then("^On HtmlCss I check the response code is \"(.*)\"$")
  public void iCheckResponseCodeHtml(int msg){
    int response = hooks.getResponseCode(properties.getHtmlCssURL());
    if (response == msg){
      log.info("The codes are a match("+ msg + "))");
    }else{
      log.error("The codes do not match. Expected : " + msg + " found " + response);
    }
    assertThat(response, is(msg));
  }

  @Then("^On HtmlCss I see that there are no errors in the console log$")
  public void iCheckLogsForErrorsHtml(){
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
