package demo.spring.selenium.pages;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public abstract class W3Page {
	
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

}

