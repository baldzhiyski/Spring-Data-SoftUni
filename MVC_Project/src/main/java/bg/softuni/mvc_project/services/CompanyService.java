package bg.softuni.mvc_project.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CompanyService {
    boolean areImported();

    String readCompaniesFromFile() throws IOException;

    void importCompanies() throws IOException, JAXBException;
}
