package index;

import java.util.ArrayList;
import java.util.List;

public class Trie {

  int numByte;

  public Trie(int numFiles) {

    this.numByte = (int) Math.ceil((double) numFiles / 8);
  }

  static class TrieNode {
    Character info; // очередная буква
    TrieNode son; // указатель на следующий уровень
    TrieNode brother; // указатель на следующий узел того же уровня
    TrieNode father; // отец
    boolean endOfWord; // является ли концом слова
    int level; // на каком уровне расположен

    byte[] bitmap; // массив битов для хранения значений

    // конструктор узла
    public TrieNode(
        Character info,
        TrieNode son,
        TrieNode brother,
        TrieNode father,
        boolean endOfWord,
        int level,
        byte[] bitmap) {
      this.info = info;
      this.son = son;
      this.brother = brother;
      this.father = father;
      this.endOfWord = endOfWord;
      this.level = level;

      this.bitmap = bitmap;
    }

    boolean hasBrother() {
      return this.brother != null;
    }

    boolean hasSon() {
      return this.son != null;
    }
  }

  private final TrieNode root = getRoot();

  private static TrieNode getRoot() {
    return new TrieNode(null, null, null, null, false, 0, null);
  }

  private TrieNode getSon(char nextChar, TrieNode father, int level) {
    return new TrieNode(nextChar, null, null, father, false, level, null);
  }

  private TrieNode getBrother(char nextChar, TrieNode father, int level) {
    return new TrieNode(nextChar, null, null, father, false, level, null);
  }

  public void add(String string, int fileIndex) { // добавление
    TrieNode curNode = root; // начинаем с вершины
    string = string.toLowerCase();

    for (int i = 0; i < string.length(); i++) {
      char nextChar = string.charAt(i); // берём следующую букву

      if (curNode.level == 0) {
        if (!curNode.hasSon()) {
          TrieNode son = getSon(nextChar, curNode, curNode.level + 1);
          if (i == string.length() - 1) {
            son.endOfWord = true;

            // проверяю существование битмапы в конце слова
            if (curNode.bitmap == null) {
              curNode.bitmap = new byte[numByte];
            }

            // добавляю индекс текущего файла в битмапу
            addFileToBitmap(curNode.bitmap, fileIndex);
          }
          curNode.son = son;
        }
        curNode = curNode.son;
      }

      while (curNode.info != nextChar && curNode.hasBrother()) { // идём по одному уровню
        curNode = curNode.brother;
      }

      if (curNode.info != nextChar
          && !curNode
              .hasBrother()) { // если дошли до конца уровня и не нашли букву, то добарляем брата

        TrieNode brother = getBrother(nextChar, curNode.father, curNode.level);

        if (i == string.length() - 1) {
          brother.endOfWord = true;
          if (curNode.bitmap == null) {
            curNode.bitmap = new byte[numByte];
          }
          addFileToBitmap(curNode.bitmap, fileIndex);
        }
        curNode.brother = brother;
        curNode = curNode.brother;
      } else if (curNode.info == nextChar
          && (i == string.length() - 1)) { // если нашли букву и она последняя в слове
        curNode.endOfWord = true;
        if (curNode.bitmap == null) {
          curNode.bitmap = new byte[numByte];
        }
        addFileToBitmap(curNode.bitmap, fileIndex);
      }

      if (curNode.hasSon()) {
        curNode = curNode.son; // переходим на след уровень
      } else if (i != string.length() - 1 && !curNode.hasSon()) { // если нет сына, то добавляем его
        char letter = string.charAt(i + 1); // следующая буква слова
        TrieNode son = getSon(letter, curNode, curNode.level + 1);
        curNode.son = son;
        curNode = curNode.son;
      }
    }
  }

  public void addFileToBitmap(byte[] bitmap, int fileIndex) {
    int byteIndex = fileIndex / 8;
    int bitIndex = fileIndex % 8;
    bitmap[byteIndex] |= 1 << bitIndex;
  }

  public void printBitmap(byte[] bitmap) {
    for (byte b : bitmap) {
      String s = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
      System.out.println(s);
    }
  }

  public boolean find(String string) {

    TrieNode curNode = root;
    string = string.toLowerCase();

    for (int i = 0; i < string.length(); i++) {
      char nextChar = string.charAt(i);
      if (curNode.hasSon()) {
        curNode = curNode.son; // переходим на нижний уровень
      } else {
        return false;
      }

      while (curNode.info != nextChar
          && curNode.hasBrother()) { // пока не найдём нужную букву в уровне
        curNode = curNode.brother;
      }

      if (curNode.info != nextChar)
        return false; // если дошли до конца уровня и не нашли след букву
      if ((i == string.length() - 1) && !curNode.endOfWord)
        return false; // если нашли,но поледняя буква не последняя
    }
    return true;
  }

  protected byte[] getBitmap(String string) {

    TrieNode curNode = root;
    string = string.toLowerCase();

    for (int i = 0; i < string.length(); i++) {
      char nextChar = string.charAt(i);
      if (curNode.hasSon()) {
        curNode = curNode.son; // переходим на нижний уровень
      } else {
        return curNode.bitmap;
      }

      while (curNode.info != nextChar
          && curNode.hasBrother()) { // пока не найдём нужную букву в уровне
        curNode = curNode.brother;
      }

      if (curNode.info != nextChar) {
        return curNode.bitmap; // если дошли до конца уровня и не нашли след букву
      }
      if ((i == string.length() - 1) && !curNode.endOfWord) {
        return curNode.bitmap; // если нашли,но последняя буква не последняя
      }
    }
    return curNode.bitmap;
  }

  public List<Integer> getFoundFilesIndexes(String query) {

    List<Integer> listFoundFilesIndexes = new ArrayList<>();

    byte[] bitmap;
    int byteCnt = 0;
    if ((bitmap = getBitmap(query)) != null) {
      for (byte b : bitmap) {
        for (byte i = 0; i < 8; i++) {
          if ((b & (1 << i & 0xff)) != 0) {
            listFoundFilesIndexes.add((byteCnt * 8) + i);
          }
        }
        byteCnt++;
      }
    }
    return listFoundFilesIndexes;
  }
}
