package geo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by ehm on 25.03.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan("geo")
public class WebAppConfig {
}
