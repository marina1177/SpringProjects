package index;

import java.io.File;
import java.io.FileFilter;

public class TxtFileFilter implements FileFilter {
  @Override
  public boolean accept(File file) {
    if (!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead()) {
      return file.getName().toLowerCase().endsWith(AppConstants.FILETYPE);
    } else {
      return false;
    }
  }
}
