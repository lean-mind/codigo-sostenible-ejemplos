import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllXmlFilesFromFolderPathShould {
    final Path path = Path.of(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources");

    @Test
    public void read_only_xml_files_from_folder_path() throws IOException {
        final String fileExtension = ".xml";
        List<Path> paths = new FileFinder().findAllFiles(path, fileExtension);
        assertThat(paths).hasSize(2);
    }

}
