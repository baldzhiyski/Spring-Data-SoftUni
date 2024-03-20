package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.domain.dtos.EmployeeDto;
import bg.softuni.mvc_project.domain.dtos.EmployeeDtoBasic;
import bg.softuni.mvc_project.domain.dtos.wrapper.EmployeeWrapper;
import bg.softuni.mvc_project.domain.entity.Company;
import bg.softuni.mvc_project.domain.entity.Employee;
import bg.softuni.mvc_project.domain.entity.Project;
import bg.softuni.mvc_project.repositories.CompanyRepository;
import bg.softuni.mvc_project.repositories.EmployeeRepository;
import bg.softuni.mvc_project.repositories.ProjectRepository;
import bg.softuni.mvc_project.services.EmployeeService;
import bg.softuni.mvc_project.utils.ValidationUtils;
import bg.softuni.mvc_project.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.mvc_project.constants.Paths.PATH_TO_EMPLOYEES;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private ModelMapper mapper;

    private ProjectRepository projectRepository;

    private ValidationUtils validationUtils;

    private XmlParser xmlParser;

    private CompanyRepository companyRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(ModelMapper mapper, ProjectRepository projectRepository, ValidationUtils validationUtils, XmlParser xmlParser, CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }
    @Override
    public boolean areImported() {
        return this.employeeRepository.count()>0;
    }

    @Override
    public String readEmployeesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_EMPLOYEES));
    }

    @Override
    public void importEmployees() throws IOException, JAXBException {
        List<EmployeeDto> employeesDtos = this.xmlParser.xmlParse(PATH_TO_EMPLOYEES, EmployeeWrapper.class)
                .getEmployeeDtos()
                .stream().collect(Collectors.toList());

        Set<Employee> employees = employeesDtos.stream()
                .filter(employeeDto -> {
                    boolean isValid = this.validationUtils.isValid(employeeDto);

                    if (this.companyRepository.findFirstByName(employeeDto.getProject().getCompany().getName()).isEmpty()) {
                        isValid = false;
                    }
                    if (this.projectRepository.findFirstByName(employeeDto.getProject().getName()).isEmpty()) {
                        isValid = false;
                    }
                    return isValid;
                })
                .map(employeeDto -> {
                    Employee employee = this.mapper.map(employeeDto, Employee.class);
                    Company company = this.companyRepository.findFirstByName(employeeDto.getProject().getCompany().getName()).get();
                    Project project = this.projectRepository.findFirstByName(employeeDto.getProject().getName()).get();

                    project.setCompany(company);
                    employee.setProject(project);
                    return employee;
                })
                .collect(Collectors.toSet());

        this.employeeRepository.saveAllAndFlush(employees);
    }

    @Override
    public String exportEmployeesOlderThan25() {
        StringBuilder builder = new StringBuilder();

        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25)
                .orElseThrow();

        employees.stream()
                .map(employee -> this.mapper.map(employee, EmployeeDtoBasic.class))
                .forEach(employeeDto -> builder.append(employeeDto.toString()).append(System.lineSeparator()));

        return builder.toString();
    }
}
