package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.ApartmentDto;
import softuni.exam.models.dto.wrapper.ApartmentsWrapper;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.VALID_APARTMENT;
import static softuni.exam.constants.Paths.PATH_TO_APARTMENTS;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private ApartmentRepository apartmentRepository;

    private TownRepository townRepository;

    private ValidationUtils validationUtils;

    private XmlParser xmlParser;

    private ModelMapper mapper;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count()>0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_APARTMENTS));
    }

    @Override
    @Transactional
    public String importApartments() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();

        Set<ApartmentDto> apartmentDtos = this.xmlParser.xmlParse(PATH_TO_APARTMENTS, ApartmentsWrapper.class)
                .getApartmentDtos()
                .stream().collect(Collectors.toSet());

        apartmentDtos.stream()
                .filter(apartmentDto -> {
                    boolean isValid = this.validationUtils.isValid(apartmentDto);

                    if(this.townRepository.findFirstByTownName(apartmentDto.getTown()).isEmpty()){
                        isValid=false;
                    }
                    if (this.apartmentRepository.findFirstByTown_TownNameAndArea(apartmentDto.getTown(),apartmentDto.getArea()).isPresent()) {
                        isValid=false;
                    }
                    if(isValid){
                        builder.append(String.format(VALID_APARTMENT,apartmentDto.getApartmentType(),
                                apartmentDto.getArea()))
                                .append(System.lineSeparator());
                    }else{
                        builder.append(INVALID_APARTMENT)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(apartmentDto -> {
                    Apartment apartment = this.mapper.map(apartmentDto, Apartment.class);
                    Town town = this.townRepository.findFirstByTownName(apartmentDto.getTown()).get();

                    apartment.setTown(town);
                    return apartment;
                }).forEach(this.apartmentRepository::saveAndFlush);

        return builder.toString();
    }
}
