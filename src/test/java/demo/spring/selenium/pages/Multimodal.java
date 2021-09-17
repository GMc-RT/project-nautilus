package demo.spring.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class Multimodal extends W3Page{	
	
	public Multimodal(WebDriver driver) {
		PageFactory.initElements(driver, this);	
	}

 
}

