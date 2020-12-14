package parser;

import java.io.File;
import java.io.FileFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
@SpringBootApplication
public class App {

  public static void main(String[] args) {

    SpringApplication.run(App.class, args);
    // имплементация контекста сканируется BeanDefinitionReader
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

    //вытащить из контекста property
    File rootFile = context.getBean(AppConfiguration.class).getJsonFile();
    String topicName = context.getBean(AppConfiguration.class).getTopicName();

    log.info("topic: " + topicName);
    System.out.println("path: " + rootFile.getName());

    // вытаскиваю Bean из контекста по классу/интерфейсу
    Filter filter = context.getBean(JsonFileFilter.class);
    filter.accept(rootFile);

    context.close();

  }

  private static void recoursiveProcess(File rootFile, Filter filter) {

    if (rootFile.exists()) {
      if (rootFile.isDirectory()) {
        File[] dirFiles = rootFile.listFiles();
        if (dirFiles != null) {
          for (File file : dirFiles) {
            if (file.isDirectory()) {
              recoursiveProcess(file, filter);
            } else if (filter.accept(file)) {
              log.info("OK - " + file.getName());
            } else {
              log.info("KO - " + file.getName());
            }
          }
        }
      }
    }
  }
}

//  private static void jsonProcess(File jsonFile) {
//    // exist json, empty json, null? (потомучто испльзую как самостоятельный метод для одельного файла
//
//    MyJsonParser parser = new MyJsonParser(jsonFile);
//    parser.printJsonName();
//    ArrayList<ArrayList<String>> itemsList = parser.getItemsList();
//    if (itemsList == null) {
//      return;
//    }
//    for (ArrayList<String> msg : itemsList){
//      System.out.println("send to kafka: "+ msg);
//    }
//  }
//


