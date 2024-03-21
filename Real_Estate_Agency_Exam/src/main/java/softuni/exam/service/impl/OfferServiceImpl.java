package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.OfferDto;
import softuni.exam.models.dto.wrapper.OfferWrapper;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_OFFER;
import static softuni.exam.constants.Messages.VALID_OFFER;
import static softuni.exam.constants.Paths.PATH_TO_OFFERS;

@Service
public class OfferServiceImpl implements OfferService {
    private ApartmentRepository apartmentRepository;

    private AgentRepository agentRepository;

    private OfferRepository offerRepository;

    private ValidationUtils validationUtils;

    private XmlParser xmlParser;

    private ModelMapper mapper;

    @Autowired
    public OfferServiceImpl(ApartmentRepository apartmentRepository, AgentRepository agentRepository, OfferRepository offerRepository, ValidationUtils validationUtils, XmlParser xmlParser, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.offerRepository = offerRepository;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_OFFERS));
    }

    @Override
    @Transactional
    public String importOffers() throws IOException, JAXBException {
        StringBuilder builder = new StringBuilder();
        Set<OfferDto> offersDtos = this.xmlParser.xmlParse(PATH_TO_OFFERS, OfferWrapper.class)
                .getOffers().stream().collect(Collectors.toSet());

        offersDtos.stream()
                .filter(offerDto -> {
                    boolean isValid = this.validationUtils.isValid(offerDto);
                    if(this.agentRepository.findFirstByFirstName(offerDto.getAgent().getFirstName()).isEmpty()){
                        isValid=false;
                    }
                    if (this.apartmentRepository.findFirstById(offerDto.getApartment().getId()).isEmpty()) {
                        isValid=false;
                    }
                    if(isValid){
                        builder.append(String.format(VALID_OFFER,offerDto.getPrice()))
                                .append(System.lineSeparator());
                    }else{
                        builder.append(INVALID_OFFER)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(offerDto ->{
                    Offer offer = this.mapper.map(offerDto, Offer.class);
                    Agent agent = this.agentRepository.findFirstByFirstName(offerDto.getAgent().getFirstName()).get();
                    Apartment apartment = this.apartmentRepository.findFirstById(offerDto.getApartment().getId()).get();

                    offer.setApartment(apartment);
                    offer.setAgent(agent);

                    return offer;
                })
                .forEach(this.offerRepository::saveAndFlush);
        return builder.toString();
    }

    @Override
    public String exportOffers() {
        return null;
    }
}
