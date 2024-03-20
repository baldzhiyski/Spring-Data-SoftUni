package bg.softuni.mvc_project.services;

import java.io.IOException;

public interface CompanyService {
    boolean areImported();

    String readCompaniesFromFile() throws IOException;

    String importCompanies() throws IOException;
}
