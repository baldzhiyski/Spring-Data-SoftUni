package bg.softuni.mvc_project.web.controllers;

import bg.softuni.mvc_project.services.CompanyService;
import bg.softuni.mvc_project.services.EmployeeService;
import bg.softuni.mvc_project.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class ImportController {
    private CompanyService companyService;
    private ProjectService projectService;

    private EmployeeService employeeService;

    @Autowired
    public ImportController(CompanyService companyService, ProjectService projectService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/import/xml")
    public String index(Model model) {
        boolean companiesImported = this.companyService.areImported();
        boolean employeesImported = this.employeeService.areImported();
        boolean projectsImported = this.projectService.areImported();

        boolean[] importStatuses = { companiesImported, employeesImported, projectsImported };

        model.addAttribute("areImported", importStatuses);

        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String viewImportCompanies(Model model) throws IOException {
        String companiesXML = this.companyService.readCompaniesFromFile();

        model.addAttribute("companies", companiesXML);

        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String doImportCompanies() throws JAXBException, IOException {
        this.companyService.importCompanies();

        return "redirect:/import/xml";
    }
    @GetMapping("/import/projects")
    public String viewImportProject(Model model) throws IOException {
        String projectsXML = this.projectService.readProjectsFromFile();

        model.addAttribute("projects",projectsXML);

        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String doImportProjects() throws JAXBException, IOException {
        this.projectService.importProjects();

        return "redirect:/import/xml";
    }

}
