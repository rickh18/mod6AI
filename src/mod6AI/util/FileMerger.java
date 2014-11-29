package mod6AI.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Created by Student on 26-11-2014.
 */
public abstract class FileMerger {

    public static String merge(String path) {
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File[] txtFiles = dir.listFiles((d, n) -> n.endsWith(".txt"));
        String out = Arrays.asList(txtFiles).parallelStream()
                                            .filter(File::isFile)
                                            .map(FileMerger::readFile)
                                            .reduce("", (a, b) -> a + "\n" + b);

        return out;
    }

    public static String readFile(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        String out = null;
        try {
            out = Files.lines(file.toPath()).reduce("", (a, b) -> a + "\n" + b);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }
}
