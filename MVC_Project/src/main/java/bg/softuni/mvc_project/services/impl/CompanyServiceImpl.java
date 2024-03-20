package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.services.CompanyService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return null;
    }

    @Override
    public String importCompanies() throws IOException {
        return null;
    }
}
