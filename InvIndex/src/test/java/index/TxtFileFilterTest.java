package index;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class TxtFileFilterTest {

	@Test
	void FilteringFilesTxtTest() {
		FileFilter filter = new TxtFileFilter();
		File rootFiles = new File("src/test/resources");

		ArrayList<File> expectedFiles = new ArrayList<>();
		ArrayList<File> actualFiles = new ArrayList<>();

		if (rootFiles.isDirectory()) {
			System.out.println("searching at " + rootFiles.getAbsolutePath());
			File[] dirFiles = rootFiles.listFiles();
			if (dirFiles != null) {
				for (File file : dirFiles) {
					if (file.getName().toLowerCase().endsWith(AppConstants.FILETYPE)) {
						expectedFiles.add(file);
						System.out.println("selected file: " + file.getName());
					}
					if (filter.accept(file)) {
						actualFiles.add(file);
						System.out.println("filtered file: " + file.getName());
					}
				}
			}
			assertArrayEquals(expectedFiles.toArray(), actualFiles.toArray());
		}
	}
}
