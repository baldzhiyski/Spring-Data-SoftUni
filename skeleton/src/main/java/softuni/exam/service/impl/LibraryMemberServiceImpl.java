package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.models.dto.LibraryMemberDto;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.Messages.INVALID_MEMBER;
import static softuni.exam.constants.Messages.VALID_MEMBER;
import static softuni.exam.constants.Paths.PATH_TO_MEMBERS;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private LibraryMemberRepository libraryMemberRepository;
    private Gson gson;
    private ModelMapper mapper;
    private ValidationUtils validationUtils;

    @Autowired
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, ModelMapper mapper, ValidationUtils validationUtils) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TO_MEMBERS));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(this.gson.fromJson(readLibraryMembersFileContent(), LibraryMemberDto[].class))
                .filter(libraryMemberDto -> {
                    boolean isValid = this.validationUtils.isValid(libraryMemberDto);
                    if (this.libraryMemberRepository.findFirstByPhoneNumber(libraryMemberDto.getPhoneNumber()).isPresent()) {
                        isValid = false;
                    }
                    builder.append(isValid? String.format(VALID_MEMBER,libraryMemberDto.getFirstName(),libraryMemberDto.getLastName()) :
                            INVALID_MEMBER).append(System.lineSeparator());
                    return isValid;
                })
                .map(libraryMemberDto -> mapper.map(libraryMemberDto, LibraryMember.class))
                .forEach(libraryMemberRepository::saveAndFlush);
        return builder.toString();
    }
}
