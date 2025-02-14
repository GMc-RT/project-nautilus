package demo.spring.selenium.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class BadPage {
	
	public WebDriver driver;

	@FindBy(tagName = "a") 
	List<WebElement> allLinks;

	@FindBy(className = "title")
	WebElement pageHeader;

	public String getHeaderText(){
		return pageHeader.getText();
	}
	
	public List<WebElement> getAllLinks(){
		return allLinks;
	}

	public BadPage(WebDriver adriver) {
		driver = adriver;
		PageFactory.initElements(driver, this);
		
	}

	

}

