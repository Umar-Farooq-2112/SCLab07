package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Task1.FileExplorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileExplorerTest {

    private File testDirectory;

    @Before
    public void setUp() throws IOException {
        testDirectory = Files.createTempDirectory("FileExplorerTest").toFile();
        testDirectory.mkdir();

        createTestFiles();
    }

    @After
    public void tearDown() {
        for (File file : testDirectory.listFiles()) {
            file.delete();
        }
        testDirectory.delete();
    }

    private void createTestFiles() throws IOException {
        new File(testDirectory, "testfile.txt").createNewFile();
        new File(testDirectory, "TestFile.txt").createNewFile();
        new File(testDirectory, "samplefile.txt").createNewFile();

        File subDir = new File(testDirectory, "subdir");
        subDir.mkdir();

        new File(subDir, "testfile.txt").createNewFile();
        new File(subDir, "TestFile.txt").createNewFile();
    }

    @Test
    public void testSearchMultipleFilesFound() {
        FileExplorer.getFoundFiles().clear();
        String[] filesToSearch = {"testfile.txt", "TestFile.txt"};

        for (String fileName : filesToSearch) {
            FileExplorer.searchFile(testDirectory, fileName, false);
        }

        assertTrue(FileExplorer.getFoundFiles().contains(new File(testDirectory, "testfile.txt").getAbsolutePath()));
        assertTrue(FileExplorer.getFoundFiles().contains(new File(testDirectory, "subdir/testfile.txt").getAbsolutePath()));
    }

    @Test
    public void testSearchFileCount() {
        FileExplorer.getFoundFiles().clear();
        String fileToSearch = "testfile.txt";

        FileExplorer.searchFile(testDirectory, fileToSearch, false);

        assertEquals(2, FileExplorer.getFoundFiles().size()); // Two occurrences in total
    }

    @Test
    public void testSearchFileCaseInsensitive() {
        FileExplorer.getFoundFiles().clear();
        String fileToSearch = "testfile.txt";

        FileExplorer.searchFile(testDirectory, fileToSearch, true);

        assertEquals(2, FileExplorer.getFoundFiles().size()); // Should find both case variations
        assertTrue(FileExplorer.getFoundFiles().contains(new File(testDirectory, "testfile.txt").getAbsolutePath()));
        assertTrue(FileExplorer.getFoundFiles().contains(new File(testDirectory, "subdir/testfile.txt").getAbsolutePath()));
    }

    @Test
    public void testSearchFileNotFound() {
        FileExplorer.getFoundFiles().clear();
        String[] filesToSearch = {"nonexistentfile.txt"};

        for (String fileName : filesToSearch) {
            FileExplorer.searchFile(testDirectory, fileName, false);
        }

        assertEquals(0, FileExplorer.getFoundFiles().size());
    }
}
