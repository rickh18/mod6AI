package mod6AI.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Student on 26-11-2014.
 */
public abstract class FileMerger {

    /**
     * Concatenates the contents of all files, with txt as extension, in the given directory.
     * @param path the path to the directory.
     * @return a String with the contents of all files with a {@code System.lineSeparator} as line and file separator.
     * @throws IOException if an I/O error occurs when opening the directory.
     */
    public static String mergeAllTextFilesInDirectory(String path) throws IOException {
        Collection<File> txtFiles = listFilesWithExtension(Paths.get(path), "txt");
        if (txtFiles == null) {
            return null;
        }

        return  mergeFiles(txtFiles);
    }

    /**
     * Returns a list of all files with the given extension in the given directory.
     * @param dir path of the directory.
     * @param ext the extension.
     * @return a collection with all files with the given extension in the given directory.
     * @throws IOException if an I/O error occurs when opening the directory.
     */
    public static Collection<File> listFilesWithExtension(Path dir, String ext) throws IOException {
        try(Stream<Path> stream = Files.list(dir)) {
            return stream.map(Path::toFile)
                            .filter(File::isFile)
                            .filter(f -> f.getName().endsWith("." + ext))
                            .collect(Collectors.toList());
        }
    }

    /**
     * Concatenates the contents of all files in the given collection without leading or trailing whitespaces.
     * @param files a collection of files to concatenate.
     * @return a String with the contents of all files with {@code System.lineSeparator} as line and file separator.
     */
    public static String mergeFiles(Collection<File> files) {
        return files.stream()
                    .filter(File::isFile)
                    .map(FileMerger::readFile)
                    .reduce("", (a, b) -> a + System.lineSeparator() + b).trim();
    }

    /**
     * Reads all lines from the given file into a single String using {@code System.lineSeparator} as separator,
     *  without leading or trailing whitespaces.
     * @param file the file to read.
     * @return a String with all lines from the file separated with {@code System.lineSeparator};
     * or null when the given argument is not a file or an {@code IOException} occurs.
     */
    public static String readFile(File file) {
        if (!file.isFile()) {
            return null;
        }

        String out;
        try(Stream<String> stream = Files.lines(file.toPath())) {
            out = stream.reduce("", (a, b) -> a + System.lineSeparator() + b).trim();
        } catch (IOException e) {
            out = null;
        }

        return out;
    }
}
