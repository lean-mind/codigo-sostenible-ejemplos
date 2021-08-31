import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import xmlmodels.Company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchXmlImporter {

    private final Dao dao;
    private final FileFinder finder;

    BatchXmlImporter(Dao dao, FileFinder finder) {
        this.dao = dao;
        this.finder = finder;
    }


    public void importFilesFrom(String folderPath) throws IOException, JAXBException, SQLException {
        List<Company> companies = getCompanies(finder.findAllXmlPathsIn(folderPath));
        insertIntoDatabase(companies);
    }

    private void insertIntoDatabase(List<Company> companies) throws SQLException {
        for (Company company : companies) {
            dao.insertCompany(company);
        }
    }

    private List<Company> getCompanies(List<Path> paths) throws JAXBException {
        ArrayList<Company> companies = new ArrayList<>();
        for (Path path : paths) {
            companies.add(parseCompanyFrom(path));
        }
        return companies;
    }

    private Company parseCompanyFrom(Path path) throws JAXBException {
        File file = new File(path.toString());
        JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Company) jaxbUnmarshaller.unmarshal(file);
    }

}
