import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder {
    public List<Path> findAllFiles(Path folderPath, String fileExtension) throws IOException {
        return Files.walk(folderPath)
                .filter(Files::isRegularFile)
                .filter(filePath ->
                        filePath.toString()
                                .endsWith(fileExtension))
                .collect(Collectors.toList());
    }
}
