package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.domain.dtos.ProjectDto;
import bg.softuni.mvc_project.domain.dtos.wrapper.ProjectWrapper;
import bg.softuni.mvc_project.domain.entity.Company;
import bg.softuni.mvc_project.domain.entity.Project;
import bg.softuni.mvc_project.repositories.CompanyRepository;
import bg.softuni.mvc_project.repositories.ProjectRepository;
import bg.softuni.mvc_project.services.ProjectService;
import bg.softuni.mvc_project.utils.ValidationUtils;
import bg.softuni.mvc_project.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.mvc_project.constants.Paths.PATH_TO_PROJECTS;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ModelMapper mapper;

    private ProjectRepository projectRepository;

    private ValidationUtils validationUtils;

    private XmlParser xmlParser;

    private CompanyRepository companyRepository;


    @Autowired
    public ProjectServiceImpl(ModelMapper mapper, ProjectRepository projectRepository, ValidationUtils validationUtils, XmlParser xmlParser, CompanyRepository companyRepository) {
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean areImported() {
        return this.projectRepository.count()>0;
    }

    @Override
    public String readProjectsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_PROJECTS));
    }

    @Override
    @Transactional
    public void importProjects() throws IOException, JAXBException {
        List<ProjectDto> dtos = this.xmlParser.xmlParse(PATH_TO_PROJECTS, ProjectWrapper.class)
                .getProjectDtoList()
                .stream().collect(Collectors.toList());

        dtos.stream()
                .filter(projectDto -> {
                    boolean isValid = this.validationUtils.isValid(projectDto);

                    if(this.companyRepository.findFirstByName(projectDto.getCompany().getName()).isEmpty()){
                        isValid=false;
                    }
                    return isValid;
                })
                .map(projectDto ->{
                    Project mapped = this.mapper.map(projectDto, Project.class);
                    Company company =this.companyRepository.findFirstByName(projectDto.getCompany().getName()).get();
                    mapped.setCompany(company);
                    return mapped;
                })
                .forEach(this.projectRepository::saveAndFlush);

    }

    @Override
    public String extractProjectFinished() throws JAXBException {
        StringBuilder builder = new StringBuilder();

        this.projectRepository.findAllByIsFinishedTrue()
                .orElseThrow()
                .stream().map(
                        project -> this.mapper.map(project,ProjectDto.class)
                )
                .forEach(projectDto -> builder.append(projectDto.toString())
                        .append(System.lineSeparator()));

        return builder.toString();
    }
}
