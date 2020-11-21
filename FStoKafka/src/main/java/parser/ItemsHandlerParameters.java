package parser;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import parser.interfaces.ItemsParameters;

@Data
public class ItemsHandlerParameters implements ItemsParameters {

  @Value("${kafka.topic:sender}")
  @NotBlank
  private String topic;

  @Valid
  @NotBlank
  private JSONArray items;
}
