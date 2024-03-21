package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.AgentDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.VALID_AGENT;
import static softuni.exam.constants.Paths.PATH_TO_AGENTS;

@Service
public class AgentServiceImpl implements AgentService {
    private Gson gson;
    private AgentRepository agentRepository;

    private ValidationUtils validationUtils;

    private TownRepository townRepository;

    private ModelMapper mapper;

    @Autowired
    public AgentServiceImpl(Gson gson, AgentRepository agentRepository, ValidationUtils validationUtils, TownRepository townRepository, ModelMapper mapper) {
        this.gson = gson;
        this.agentRepository = agentRepository;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
        this.mapper = mapper;
    }
    @Override
    public boolean areImported() {
        return this.agentRepository.count()>0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH_TO_AGENTS));
    }

    @Override
    @Transactional
    public String importAgents() throws IOException {
        StringBuilder builder = new StringBuilder();

        Set<AgentDto> dtos = Arrays.stream(this.gson.fromJson(readAgentsFromFile(), AgentDto[].class))
                .collect(Collectors.toSet());

        dtos.stream()
                .filter(agentDto -> {
                    boolean isValid = this.validationUtils.isValid(agentDto);

                    if(this.townRepository.findFirstByTownName(agentDto.getTown()).isEmpty()){
                        isValid=false;
                    }
                    if(this.agentRepository.findFirstByEmail(agentDto.getEmail()).isPresent()){
                        isValid=false;
                    }
                    if(isValid){
                        builder.append(String.format(VALID_AGENT,
                                agentDto.getFirstName(),agentDto.getLastName()))
                                .append(System.lineSeparator());
                    }else{
                        builder.append(INVALID_AGENT)
                                .append(System.lineSeparator());
                    }
                    return isValid;
                })
                .map(agentDto -> {
                    Agent agent = this.mapper.map(agentDto, Agent.class);
                    Town town = this.townRepository.findFirstByTownName(agentDto.getTown()).get();

                    agent.setTown(town);
                    return agent;
                }).forEach(this.agentRepository::saveAndFlush);

        return builder.toString();
    }
}
