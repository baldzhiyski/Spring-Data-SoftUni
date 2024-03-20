package bg.softuni.mvc_project.services;

import java.io.IOException;

public interface ProjectService {
    boolean areImported();

    String readProjectsFromFile() throws IOException;

    String importProjects() throws IOException;
}
