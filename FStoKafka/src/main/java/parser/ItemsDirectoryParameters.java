package parser;

import java.io.File;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import parser.interfaces.ItemsParameters;

@Data
public class ItemsDirectoryParameters implements ItemsParameters {


  @NotBlank(message = "Please provide a topic")
  @Value("${kafka.topic:sender}")
  private String topic;

  @Valid
  @NotBlank(message = "Please provide a directory")
  private File rootFile;
}
