package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CompanyDto;
import softuni.exam.models.dto.wrapper.CompanyWrapper;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static softuni.exam.constants.Paths.PATH_COMPANIES;

@Service
public class CompanyServiceImpl implements CompanyService {
    private ModelMapper mapper;

    private ValidationUtils validationUtils;

    private CompanyRepository companyRepository;
    private XmlParser xmlParser;
    private CountryRepository countryRepository;

    @Autowired
    public CompanyServiceImpl(ModelMapper mapper, ValidationUtils validationUtils, CompanyRepository companyRepository, XmlParser xmlParser, CountryRepository countryRepository) {
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count()>0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(PATH_COMPANIES));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        List<CompanyDto> companyDtos = this.xmlParser.xmlParse(PATH_COMPANIES, CompanyWrapper.class)
                .getCompanyDtoList().stream().collect(Collectors.toList());

        companyDtos.stream()
                .filter(companyDto -> {
                    boolean isValid = this.validationUtils.isValid(companyDto);
                    if (this.countryRepository.findFirstById(companyDto.getCountryId()).isEmpty()) {
                        isValid=false;
                    }
                    if(this.companyRepository.findFirstByName(companyDto.getName()).isPresent()){
                        isValid=false;
                    }
                    String toAppend = isValid ? String.format(Messages.VALID_COMPANY,companyDto.getName(),companyDto.getCountryId())
                            : Messages.INVALID_COMPANY;
                    builder.append(toAppend).append(System.lineSeparator());
                    return isValid;
                })
                .map(companyDto -> {
                    Company company = this.mapper.map(companyDto, Company.class);
                    Country country = this.countryRepository.getById(companyDto.getCountryId());
                    company.setCountry(country);
                    return company;
                }).forEach(this.companyRepository::saveAndFlush);
        return builder.toString();
    }
}
