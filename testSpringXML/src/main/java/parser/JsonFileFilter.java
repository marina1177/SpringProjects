package parser;

import java.io.File;
import java.io.FileFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonFileFilter implements Filter {

//  @InjectionRandomString(length = 20)
//  private String randomString;

  private  String fileType;

  // чтобы определить поле - спринг вызовет сеттер
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public void filterInit(){
    System.out.println("FILTER INIT");
  }
  public void filterDestroy(){
    System.out.println("FILTER DESTROY");
//    System.out.println(randomString);
  }

  @Override
  public boolean accept(File file) {
    if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()) {
      log.info("OK - " + file.getName());
      return file.getName().toLowerCase().endsWith(fileType);
    } else {
      log.info("KO - " + file.getName());
      return false;
    }
  }
}
