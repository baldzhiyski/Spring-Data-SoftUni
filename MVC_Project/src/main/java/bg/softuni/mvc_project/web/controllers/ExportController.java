package bg.softuni.mvc_project.web.controllers;

import bg.softuni.mvc_project.services.EmployeeService;
import bg.softuni.mvc_project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.bind.JAXBException;

@Controller
public class ExportController {
    private final ProjectService projectService;

    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("export/project-if-finished")
    public String exportFinalizedProjects(Model model) throws JAXBException {
        model.addAttribute("projectsIfFinished",this.projectService.extractProjectFinished());

        return "export/export-project-if-finished";
    }

    @GetMapping("export/employees-with-age")
    public String exportEmployeesOlderThan25(Model model) throws JAXBException {
        model.addAttribute("employeesAbove",this.employeeService.exportEmployeesOlderThan25());

        return "export/export-employees-with-age";
    }
}
