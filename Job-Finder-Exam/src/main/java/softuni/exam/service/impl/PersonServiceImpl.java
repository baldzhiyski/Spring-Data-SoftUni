package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.models.dto.PersonDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_PERSON;
import static softuni.exam.constants.Messages.VALID_PERSON;
import static softuni.exam.constants.Paths.PATH_PEOPLE;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    private CountryRepository countryRepository;

    private ModelMapper mapper;
    private ValidationUtils validationUtils;

    private Gson gson;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, CountryRepository countryRepository, ModelMapper mapper, ValidationUtils validationUtils, Gson gson) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count()>0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PATH_PEOPLE));
    }

    @Override
    @Transactional
    public String importPeople() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        Set<PersonDto> personDtos = Arrays.stream(this.gson.fromJson(readPeopleFromFile(), PersonDto[].class))
                .collect(Collectors.toSet());

        personDtos.stream()
                .filter(personDto -> {
                    boolean isValid = this.validationUtils.isValid(personDto);
                    if (this.countryRepository.findFirstById(personDto.getCountry()).isEmpty()) {
                        isValid = false;
                    }
                    if (this.personRepository.findFirstByFirstNameOrEmailOrPhone(personDto.getFirstName(), personDto.getEmail(),
                            personDto.getPhone()).isPresent()) {
                        isValid = false;
                    }
                    String toAppend = isValid ? String.format(VALID_PERSON, personDto.getFirstName(), personDto.getLastName())
                            : INVALID_PERSON;
                    builder.append(toAppend).append(System.lineSeparator());
                    return isValid;
                })
                .map(personDto -> {
                    Person person = this.mapper.map(personDto, Person.class);
                    Country country = this.countryRepository.getById(personDto.getCountry());
                    person.setCountry(country);
                    return person;
                })
                .forEach(this.personRepository::saveAndFlush);

        return builder.toString();
    }
}
