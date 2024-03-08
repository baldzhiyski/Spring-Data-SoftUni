package bg.softuni.mapping_objects_lab.services;

import bg.softuni.mapping_objects_lab.entities.dtos.AddressDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.AddressWithIdDTO;

public interface AddressService {
    AddressWithIdDTO create(AddressDTO data);
}
