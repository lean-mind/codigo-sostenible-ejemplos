import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class BatchXmlImporterShould {

    final Path path = Path.of(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources");

    @Test
    public void import_xml_into_database() throws JAXBException, IOException, SQLException {
        Dao dao = new Dao();
        FileFinder finder = new FileFinder();
        BatchXmlImporter batchXmlImporter = new BatchXmlImporter(dao, finder);
        dao.clearTables();
        final String fileExtension = "xml";

        batchXmlImporter.importFiles(path, fileExtension);

        var companies = dao.getAllCompanies();
        assertThat(companies).hasSize(2);
    }
}
