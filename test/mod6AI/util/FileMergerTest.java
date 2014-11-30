package mod6AI.util;

import mod6AI.ai.Tokenizer;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class FileMergerTest {

    static final String TEST_DIR = "test_data/";

    static final File EMPTY_FILE = new File(TEST_DIR + "empty.txt");
    static final String EMPTY_CONTENTS = "";

    static final File MULTI_LINE_CRLF_FILE = new File(TEST_DIR + "multiLine_CRLF.txt");
    static final String MULTI_LINE_CRLF_CONTENTS = "This test file contains two lines of text.\r\n" +
                                                    "And also ends with a CR and LF.\r\n";

    static final File MULTI_LINE_LF_FILE = new File(TEST_DIR + "/" + "multiLine_LF.txt");
    static final String MULTI_LINE_LF_CONTENTS = "This test file contains two lines of text.\n" +
                                                    "And also ends with a LF.\n";

    static final File TEST_FILE = new File(TEST_DIR + "test.txt");
    static final String TEST_CONTENTS = "This is a simple test file. And has no newline characters.";

    @Test
    public void testListFilesWithExtension() throws Exception {
        ArrayList<File> expected = new ArrayList<>();
        Collection<File> files;

        files = FileMerger.listFilesWithExtension(Paths.get(TEST_DIR), "foo");
        assertEquals(expected, files);

        expected.add(EMPTY_FILE); expected.add(MULTI_LINE_CRLF_FILE);
        expected.add(MULTI_LINE_LF_FILE); expected.add(TEST_FILE);
        files = FileMerger.listFilesWithExtension(Paths.get(TEST_DIR), "txt");

        assertEquals(expected, files);
    }

    @Test
    public void testReadFile() throws Exception {
        assertEquals(EMPTY_CONTENTS, FileMerger.readFile(EMPTY_FILE));
        String expected = MULTI_LINE_CRLF_CONTENTS.replaceAll("\\r\\n", System.lineSeparator()).trim();
        assertEquals(expected, FileMerger.readFile(MULTI_LINE_CRLF_FILE));
        expected = MULTI_LINE_LF_CONTENTS.replaceAll("\\n", System.lineSeparator()).trim();
        assertEquals(expected, FileMerger.readFile(MULTI_LINE_LF_FILE));
        assertEquals(TEST_CONTENTS, FileMerger.readFile(TEST_FILE));
    }

    @Test
    public void testMergeFiles() throws Exception {
        String expected = MULTI_LINE_CRLF_CONTENTS + MULTI_LINE_LF_CONTENTS;
        expected = expected.replaceAll("[\\r]?\\n", System.lineSeparator()).trim();
        ArrayList<File> files = new ArrayList<>();
        files.add(MULTI_LINE_CRLF_FILE); files.add(MULTI_LINE_LF_FILE);
        assertEquals(expected, FileMerger.mergeFiles(files));
    }

    @Test
    public void testMergeAllTextFilesInDirectory() throws Exception {
        Collection<String> expectedTokens =
                Tokenizer.tokenize(MULTI_LINE_CRLF_CONTENTS + MULTI_LINE_LF_CONTENTS + TEST_CONTENTS);
        assertEquals(expectedTokens, Tokenizer.tokenize(FileMerger.mergeAllTextFilesInDirectory(TEST_DIR)));
    }
}