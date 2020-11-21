package parser.controllers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestResponse {

  private final String topicName;
  private final double totalSentMsgs;
  private final int successSentMsgs;
}
