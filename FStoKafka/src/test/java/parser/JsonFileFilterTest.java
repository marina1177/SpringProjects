package parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import parser.domain.JsonFileFilter;


class JsonFileFilterTest {

  static private FileFilter filter;
  static final Random RAND = new Random();
  static final String FILETYPE = ".json";

  @TempDir Path tempDir;

  public Set<File> generateFiles() throws IOException {

    Set<File> saveFiles = new HashSet();
    String[] filePostfix = new String[] {".json", ".txt", ".csv", ".jpg"};
    RandomStringGenerator generator =
        new RandomStringGenerator.Builder().withinRange('a', 'z').build();

    int numFiles = RAND.nextInt(50);
    for (int i = 0; i < numFiles; i++) {
      String newPostfix = filePostfix[(int) (Math.random() * 4)];

      String newFileName = String.format("%s%s", generator.generate(10), newPostfix);

      Path newFilePath = Files.createFile(tempDir.resolve(newFileName));
      File newFile = newFilePath.toFile();

      if (newFileName.endsWith(FILETYPE)) {
        saveFiles.add(newFile);
      }
    }
    return saveFiles;
  }

  @BeforeAll
  static void SetUp() {
    filter = new JsonFileFilter(FILETYPE);
  }


  @Test
  void filteringFilesJsonTest() throws IOException {

    Set<File> expectedFiles = generateFiles();
    Set<File> actualFiles = new HashSet();
    File[] dirFiles = tempDir.toFile().listFiles();
    if (dirFiles != null) {
      for (File file : dirFiles) {
        if (filter.accept(file)) {
          actualFiles.add(file);
        }
      }
    }
    assertTrue(expectedFiles.equals(actualFiles));
  }

  @Test
  void nullTest() {
    assertFalse(filter.accept((File) null));
  }
}
