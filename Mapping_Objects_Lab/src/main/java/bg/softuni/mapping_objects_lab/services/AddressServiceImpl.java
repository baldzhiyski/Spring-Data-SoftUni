package bg.softuni.mapping_objects_lab.services;

import bg.softuni.mapping_objects_lab.entities.Address;
import bg.softuni.mapping_objects_lab.entities.dtos.AddressDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.AddressWithIdDTO;
import bg.softuni.mapping_objects_lab.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    private AddressRepository addressRepository;
    private ModelMapper mapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper mapper) {
        this.addressRepository = addressRepository;
        this.mapper = mapper;
    }

    @Override
    public AddressWithIdDTO create(AddressDTO data) {
        Address address = mapper.map(data, Address.class);
        Address saved = this.addressRepository.save(address);

        return  this.mapper.map(saved,AddressWithIdDTO.class);
    }
}
