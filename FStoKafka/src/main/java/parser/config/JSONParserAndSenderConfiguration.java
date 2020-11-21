package parser.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import parser.domain.JsonParser;
import parser.JSONParserAndSender;

@Configuration
@RequiredArgsConstructor
public class JSONParserAndSenderConfiguration {

  private final SenderConfiguration senderConfiguration;

  @Value("${spring.fieldName}")
  private String fieldName;

  @Bean
  public JsonParser parser() {
    return new JsonParser(fieldName);
  }

  @Bean
  public JSONParserAndSender jsonParserAndSender() {
    return new JSONParserAndSender(parser(), senderConfiguration.sender());
  }

}
