package parser;

import java.io.File;

public interface Filter {

  public boolean accept(File file);
}
