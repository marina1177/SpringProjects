package index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class App {

  public static void main(String[] args) {

    File fileDataDir = new File(AppConstants.DATADIR);
    // считать папку - проверить что это папка и что она существует
    if (fileDataDir.exists()) {

      // считать слово для поиска
      System.out.println(
          AppConstants.ANSI_BLUE + "enter a search word: " + AppConstants.ANSI_RESET);
      Scanner sc = new Scanner(System.in);
      String myWord = "";
      if (sc.hasNext()) {
        myWord = (sc).nextLine();
      }

      // создать лист текстовых файлов, сохранить их количество в константу(по этому числу выделю
      // битмап)
      List<String> listFileNames = searchFiles(fileDataDir, new TxtFileFilter());
      if (listFileNames.isEmpty()) {
        System.out.println(
            AppConstants.ANSI_BLUE + "no matching files were found(" + AppConstants.ANSI_RESET);
        return;
      }
      // если лист не пустой и есть где искать слова:
      // создать дерево, передать количество байт для битмапы
      Trie prefTrie = new Trie(listFileNames.size());

      // заполнить дерево
      fillTrie(prefTrie, listFileNames);
      List<String> listFoundFileNames =
          getFoundFilesNames(listFileNames, prefTrie.getFoundFilesIndexes(myWord));

      printListFileName(listFoundFileNames, myWord);

    } else {
      System.out.println("path " + fileDataDir.getPath() + " not exist");
    }
  }

  private static List<String> getFoundFilesNames(
      List<String> listFileNames, List<Integer> listFoundFilesIndexes) {

    List<String> listFoundFilesNames = new ArrayList<>();

    for (Integer indx : listFoundFilesIndexes) {
      if (indx < listFileNames.size() + 1) {
        listFoundFilesNames.add(listFileNames.get(indx));
      }
    }
    return listFoundFilesNames;
  }

  private static void printListFileName(List<String> listFoundFile, String query) {

    if (!listFoundFile.isEmpty()) {
      System.out.println(
          AppConstants.ANSI_BLUE
              + "TOKEN:"
              + "["
              + query
              + "]"
              + " is found in:"
              + AppConstants.ANSI_RESET);
      listFoundFile.forEach(System.out::println);
    } else {
      System.out.println(
          AppConstants.ANSI_RED
              + "TOKEN:"
              + "["
              + query
              + "]"
              + " is not found in "
              + AppConstants.DATADIR
              + AppConstants.ANSI_RESET);
    }
  }

  private static void fillTrie(Trie prefTrie, List<String> listFileName) {

    // для каждого файла из списка:
    // открыть, разделить слова по пробелам без знаков препинания
    for (String filename : listFileName) {

      try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String str;
        // parse file:
        while ((str = reader.readLine()) != null) {
          if (!str.isEmpty()) {
            // parse string:
            StringTokenizer st = new StringTokenizer(str, "\t\n\r,. ?!;:()[{*}]—\"“”'");
            while (st.hasMoreTokens()) {
              // add to trie:
              prefTrie.add(st.nextToken(), listFileName.indexOf(filename));
            }
          }
        }
      } catch (FileNotFoundException e) {
        System.out.println(filename + "not found");
      } catch (IOException e) {
        System.out.println("");
      }
    }
  }

  private static List<String> searchFiles(File rootFile, FileFilter filter) {

    List<String> fileNameList = new ArrayList<>();
    if (rootFile.exists()) {
      if (rootFile.isDirectory()) {
        File[] dirFiles = rootFile.listFiles();
        if (dirFiles != null) {
          for (File file : dirFiles) {
            if (file.isDirectory()) {
              searchFiles(file, filter);
            } else {
              if (filter.accept(file)) {
                fileNameList.add(file.getAbsolutePath());
              }
            }
          }
        }
      } else {
        if (filter.accept(rootFile)) {
          fileNameList.add(rootFile.getAbsolutePath());
        }
      }
    }
    return fileNameList;
  }
}
