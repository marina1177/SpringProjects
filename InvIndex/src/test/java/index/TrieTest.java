package index;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class TrieTest {
	static final int MAX_WORD_LENGTH = 15;
	static final int NUM_FILES = 50;
	static final int NUM_WORDS = 100;

	static Set<String> prefixes = new HashSet<>(NUM_WORDS * MAX_WORD_LENGTH / 2);
	static final Random RAND = new Random();
	static Trie trie = new Trie(NUM_FILES);
	static Set<String> words = new HashSet<>(NUM_WORDS);

	static char randChar() {
		return (char) (RAND.nextInt(26) + 'a');
	}

	static String randWord(int maxLen) {
		StringBuilder sb = new StringBuilder();
		int len = RAND.nextInt(maxLen) + 1;
		for (int i = 0; i < len; i++) {
			sb.append(randChar());
		}
		return sb.toString();
	}

	static String randWordPlus() {
		StringBuilder sb = new StringBuilder();
		int len = RAND.nextInt(MAX_WORD_LENGTH) + 1;
		for (int i = 0; i < len; i++) {
			sb.append(randChar());
			prefixes.add(sb.toString());
		}
		return sb.toString();
	}

	@BeforeAll //заполняю дерево рандомными словами
	static void SetUp() {
		trie.numByte =(int) Math.ceil((double) NUM_WORDS/8);
	}

	@RepeatedTest(100)
	 void FindExistWordTest() throws Exception {
		String word = randWordPlus();
			words.add(word);
			trie.add(word,1);
			assertTrue(trie.find(word));
			byte[] bitmap;
			bitmap = trie.getBitmap(word);
			// assertNotNull(());
      if (bitmap == null) assertFalse(false);
      else {
        byte b = bitmap[0];
        assertNotEquals(0, b & (1 << 1));
			}//проверяю заполнения битмап
	}

	@RepeatedTest(100)
	 void FindNotExistWordTest() throws Exception {
		String word = randWordPlus();
		assertFalse(trie.find(word));
	}
private boolean existA(List<Integer> arr, Integer a) {
		for (Integer i : arr) {
			if(i.equals(a)){
				return true;
			}
		}
		return false;
}
	@RepeatedTest(100)
	void getFoundFilesTest(){
		List<Integer> expectedIndexes = new ArrayList<>();
		List<Integer> actualIndexes = new ArrayList<>();
		Trie prefTrie = new Trie(NUM_FILES);
		//создаю рандомное слово
		String word = randWordPlus();
		// создаю набор из 10 рандомных чисел List<Integer> в диапазоне 0-NumFiles
		// добавляю слово в trie  для каждого индекса
		for (int i =0; i < 10; i++){
			Integer val =(Integer) RAND.nextInt(NUM_FILES-1);
			if(!existA(expectedIndexes, val)){
				expectedIndexes.add(val);
			}
			prefTrie.add(word,val);
		}
		actualIndexes = prefTrie.getFoundFilesIndexes(word);
		Collections.sort(actualIndexes);
		Collections.sort(expectedIndexes);

		assertArrayEquals(expectedIndexes.toArray(), actualIndexes.toArray());

	}

}