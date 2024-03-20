package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.services.ProjectService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readProjectsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importProjects() throws IOException {
        return null;
    }
}
