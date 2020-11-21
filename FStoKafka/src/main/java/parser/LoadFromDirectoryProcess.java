package parser;

import java.io.File;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import parser.domain.JsonFileFilter;


@Slf4j
@Data
public class LoadFromDirectoryProcess {

  // конструктор
  private final JsonFileFilter filter;
  private final JSONParserAndSender parser;

  @NotNull
  @Value("${DOCKER_FS:Docs}")
  private File rootFile;

  @NotNull
  @Value("${kafka.topic:sender}")
  private String topicName;

  public StatusLoader run() throws Exception {

    if (rootFile.exists()) {
      return recoursiveProcess(rootFile);
    } else {

      log.warn("path {} not exist", rootFile.getPath());
      return new StatusLoader();
    }
  }

  private StatusLoader recoursiveProcess(File rootFile) throws Exception {

    StatusLoader resStatus = new StatusLoader();

    if (rootFile.isDirectory()) {
      File[] dirFiles = rootFile.listFiles();
      if (dirFiles != null) {
        for (File file : dirFiles) {
          if (file.isDirectory()) {
            StatusLoader ls = recoursiveProcess(file);
            resStatus.addAndGetSuccessMessages(ls.getSuccessLoadMessages());
            resStatus.addAndGetFailMessages(ls.getFailLoadMessages());
          } else {
            if (filter.accept(file)) {
              StatusLoader ls = parser.jsonObjectProcess(file);
              resStatus.addAndGetSuccessMessages(
                  ls.getSuccessLoadMessages());
              resStatus.addAndGetFailMessages(ls.getFailLoadMessages());
            }
          }
        }
      }
    } else if (filter.accept(rootFile)) {
      StatusLoader ls = parser.jsonObjectProcess(rootFile);
      resStatus
          .addAndGetSuccessMessages(ls.getSuccessLoadMessages());
      resStatus.addAndGetFailMessages(ls.getFailLoadMessages());
    }
    return resStatus;
  }
}
