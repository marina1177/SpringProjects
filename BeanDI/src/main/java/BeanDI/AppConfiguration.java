package BeanDI;

import java.io.File;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@Slf4j
public class AppConfiguration {

  @Bean
  public AppProcess appProcess() {
    return new AppProcess;
  }

  @PostConstruct
  public void init() {
    log.info("app is loaded");
  }

}
