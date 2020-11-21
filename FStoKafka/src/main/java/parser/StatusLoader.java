package parser;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusLoader {

  private int successLoadMessages ; // сколько сообщений отправилось успешно
  private int failLoadMessages; // сколько сообщений зафейлили

  public int addAndGetSuccessMessages(int delta) {

    if (delta > 0) {
      successLoadMessages += delta;
    }
    return successLoadMessages;
  }

  public int addAndGetFailMessages(int delta) {

    if (delta > 0) {
      failLoadMessages += delta;
    }
    return failLoadMessages;
  }
}

