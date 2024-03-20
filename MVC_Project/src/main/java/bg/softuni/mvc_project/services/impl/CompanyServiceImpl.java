package bg.softuni.mvc_project.services.impl;

import bg.softuni.mvc_project.constants.Paths;
import bg.softuni.mvc_project.domain.dtos.CompanyDto;
import bg.softuni.mvc_project.domain.dtos.wrapper.CompanyWrapper;
import bg.softuni.mvc_project.domain.entity.Company;
import bg.softuni.mvc_project.repositories.CompanyRepository;
import bg.softuni.mvc_project.services.CompanyService;
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
import java.util.stream.Collectors;

import static bg.softuni.mvc_project.constants.Paths.PATH_TO_COMPANIES;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private ModelMapper mapper;

    private ValidationUtils validationUtils;

    private XmlParser xmlParser;
    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper, ValidationUtils validationUtils, XmlParser xmlParser) {
        this.companyRepository = companyRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count()>0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_COMPANIES));
    }

    @Override
    public void importCompanies() throws IOException, JAXBException {
        List<CompanyDto> companyDtos = this.xmlParser.xmlParse(PATH_TO_COMPANIES, CompanyWrapper.class)
                .getCompanyDtos()
                .stream().collect(Collectors.toList());

        companyDtos.stream()
                .filter(companyDto -> this.validationUtils.isValid(companyDto))
                .map(companyDto -> this.mapper.map(companyDto, Company.class))
                .forEach(this.companyRepository::saveAndFlush);
    }
}
