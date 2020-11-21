package parser;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import parser.domain.JsonParser;
import parser.interfaces.Sender;

@Slf4j
@Data
public class JSONParserAndSender {

  private final JsonParser parser;
  private final Sender sender;

  @NotNull
  @Value("${kafka.topic:sender}")
  private String topic;

  public StatusLoader jsonObjectProcess(Object obj) throws Exception{

    List<Object> itemsList = parser.getFieldRecordsList(obj);
    if (itemsList == null || itemsList.isEmpty()) {
      return new StatusLoader();
    }
    return sendItemsList(itemsList);
  }

 StatusLoader sendItemsList(List<Object> itemsList) {

    StatusLoader loadStatus = new StatusLoader();
    sender.setTopic(topic);
    for (Object msg : itemsList) {

      int status = sender.send("key", msg.toString());

      if (status == 1) {
        loadStatus.addAndGetSuccessMessages(1);
        log.info("msg: " + msg.toString());
      } else if (status == 0) {
        loadStatus.addAndGetFailMessages(1);
      }
    }
    return loadStatus;
  }
}
