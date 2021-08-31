import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder {
    public List<Path> findAllXmlPathsIn(String folderPath) throws IOException {
        return Files.walk(Path.of(folderPath))
                .filter(Files::isRegularFile)
                .filter(filePath ->
                        filePath.toString()
                                .endsWith(".xml"))
                .collect(Collectors.toList());
    }
}
