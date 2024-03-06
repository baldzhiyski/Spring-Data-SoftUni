package bg.softuni.mapping_objects_lab.services;

import bg.softuni.mapping_objects_lab.entities.Address;
import bg.softuni.mapping_objects_lab.entities.dtos.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
