package bg.softuni.mvc_project.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProjectService {
    boolean areImported();

    String readProjectsFromFile() throws IOException;

    void importProjects() throws IOException, JAXBException;

    String extractProjectFinished() throws JAXBException;
}
