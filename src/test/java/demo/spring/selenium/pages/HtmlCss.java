package demo.spring.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class HtmlCss extends W3Page{
	
	public HtmlCss(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

  

}

