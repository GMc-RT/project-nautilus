package demo.spring.selenium.config;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebDriverManager {

  private static final String DOCKER_HOST = "http://127.0.0.1";
  private static final String SELENIUM_PORT = "4444";
  private static final String CHROME = "chrome";
  private static final String FIREFOX = "firefox";
  private static final String CONTEXT = "local";

  @Autowired
  private ProjectNautilusProperties properties;

  @Bean
  @Scope("cucumber-glue")
  public WebDriver webDriverFactory() throws IOException {
    return properties.getContext().equalsIgnoreCase(CONTEXT) ? new FirefoxDriver()
        : getRemoteWebDriver(properties.getBrowser());
  }

  private WebDriver getRemoteWebDriver(String browser) throws IOException, UnsupportedOperationException {
    String remote = String.format("%s:%s/wd/hub", DOCKER_HOST, SELENIUM_PORT);
    DesiredCapabilities capabilities = null;
    if (browser.equalsIgnoreCase(CHROME)) {
      System.out.print("****************************Starting Chrome");
      capabilities = DesiredCapabilities.chrome();
      LoggingPreferences logPrefs = new LoggingPreferences();
      ChromeOptions options = new ChromeOptions();
      options.addArguments("start-maximized"); // open Browser in maximized mode
      options.addArguments("disable-infobars"); // disabling infobars
      options.addArguments("--disable-extensions"); // disabling extensions
      options.addArguments("--disable-gpu"); // applicable to windows os only
      options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
      options.addArguments("--no-sandbox"); // Bypass OS security model
      logPrefs.enable(LogType.BROWSER, Level.ALL);
      capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
      capabilities.setCapability(ChromeOptions.CAPABILITY, options);
      
      
      return new RemoteWebDriver(new URL(remote), capabilities);
    }
    if (browser.equalsIgnoreCase(FIREFOX)){
      System.out.print("****************************Starting Firefox");
      capabilities = DesiredCapabilities.firefox();
      FirefoxOptions options = new FirefoxOptions();
      options.setLogLevel(FirefoxDriverLogLevel.TRACE);
      capabilities.merge(options);

      return new RemoteWebDriver(new URL(remote), options);
    }
    else{
      log.error("FATAL: The Browser: " + browser + " is not yet supported");
      throw new UnsupportedOperationException("Not supported yet");
    }
  }

}

