package demo.spring.selenium.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties
@EnableConfigurationProperties
public class ProjectNautilusProperties{

    @Value("${badPageURL}")
    private String badPageURL;

    @Value("${multimodalURL}")
    private String multimodalURL;

    @Value("${htmlCssURL}")
    private String htmlCssURL;

    @Value("${browser}")
    private String browser;

    @Value("${context}")
    private String context;
}
