package parser.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import parser.LoadFromDirectoryProcess;
import parser.domain.JsonFileFilter;

@Configuration
@RequiredArgsConstructor
public class RefreshConfiguration {


  private final JSONParserAndSenderConfiguration parserConfiguration;

  @Value("${spring.fileType}")
  private String fileType;

  @Bean
  public JsonFileFilter filter() {
    return new JsonFileFilter(fileType);
  }

  @Bean
  public LoadFromDirectoryProcess getAppProcess() {
    return new LoadFromDirectoryProcess(
        filter(),
        parserConfiguration.jsonParserAndSender()
        );
  }
}
