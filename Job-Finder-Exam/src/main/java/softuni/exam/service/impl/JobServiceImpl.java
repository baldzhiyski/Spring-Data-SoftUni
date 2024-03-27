package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.JobDto;
import softuni.exam.models.dto.JobExtractInfoDto;
import softuni.exam.models.dto.wrapper.JobWrapper;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.PATH_JOBS;

@Service
public class JobServiceImpl implements JobService {
    private XmlParser xmlParser;
    private ModelMapper mapper;
    private JobRepository jobRepository;

    private CompanyRepository companyRepository;

    private ValidationUtils validationUtils;
    @Autowired
    public JobServiceImpl(XmlParser xmlParser, ModelMapper mapper, JobRepository jobRepository, CompanyRepository companyRepository, ValidationUtils validationUtils) {
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.jobRepository.count()>0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_JOBS));
    }

    @Override
    @Transactional
    public String importJobs() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        List<JobDto> jobDtoList = this.xmlParser.xmlParse(PATH_JOBS, JobWrapper.class)
                .getJobs().stream().toList();

        jobDtoList.stream()
                .filter(jobDto -> {
                    boolean isValid = this.validationUtils.isValid(jobDto);

                    if (this.companyRepository.findFirstById(jobDto.getCompanyId()).isEmpty()) {
                        isValid=false;
                    }
                    String toAppend = isValid ? String.format(VALID_JOB,jobDto.getTitle())
                            : INVALID_JOB;
                    builder.append(toAppend).append(System.lineSeparator());
                    return isValid;
                })
                .map(jobDto -> {
                    Job job = this.mapper.map(jobDto, Job.class);
                    Company byId = this.companyRepository.getById(jobDto.getCompanyId());
                    job.setCompany(byId);

                    byId.getJobs().add(job);
                    return job;
                })
                .forEach(this.jobRepository::saveAndFlush);

        return builder.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder builder = new StringBuilder();

        List<Job> jobs = this.jobRepository.findAllBySalaryGreaterThanAndHoursAWeekLessThanOrderBySalaryDesc(BigDecimal.valueOf(5000), 30.00);
        jobs.stream()
                .map(job -> mapper.map(job, JobExtractInfoDto.class))
                .forEach(jobExtractInfoDto -> builder.append(jobExtractInfoDto.toString()).append(System.lineSeparator()));
        return builder.toString();
    }
}
