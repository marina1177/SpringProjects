package parser;

import java.io.File;

public class AppConfiguration {

  private File rootFile;
  private String topicName;

  public void setJsonFile(File jsonFile) {
    this.rootFile = jsonFile;
  }

  public File getJsonFile() {
    System.out.println("Your file : " + rootFile.getAbsolutePath());
    return rootFile;
  }

  public void setTopicName(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicName() {
    System.out.println("Your topic : " + topicName);
    return topicName;
  }
}
