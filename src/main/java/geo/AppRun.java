package geo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


/**
 * Created by ehm on 15.04.2017.
 */
@SpringBootApplication
@EnableConfigurationProperties
@PropertySources({
        //TODO move properties to tomcat directory
        @PropertySource(value = "classpath:app.properties", ignoreResourceNotFound = false)
})
public class AppRun extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppRun.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

}
