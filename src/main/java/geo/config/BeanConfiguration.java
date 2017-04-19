package geo.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ehm on 18.04.2017.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public DozerBeanMapper mapper() {
        List<String> mappingFiles = Arrays.asList("dozer-mapping.xml");
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }
}
