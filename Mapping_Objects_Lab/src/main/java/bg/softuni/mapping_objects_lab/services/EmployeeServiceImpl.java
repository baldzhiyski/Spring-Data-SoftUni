package bg.softuni.mapping_objects_lab.services;

import bg.softuni.mapping_objects_lab.entities.Address;
import bg.softuni.mapping_objects_lab.entities.Employee;
import bg.softuni.mapping_objects_lab.entities.dtos.CreateEmployeeDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNameAndSalaryDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.EmployeeNamesDTO;
import bg.softuni.mapping_objects_lab.repositories.AddressRepository;
import bg.softuni.mapping_objects_lab.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements  EmployeeService{
    private EmployeeRepository employeeRepository;
    private AddressRepository addressRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
    }
    @Override
    @Transactional
    public Employee create(CreateEmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();
        Employee employee = mapper.map(employeeDTO, Employee.class);

        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                employeeDTO.getAddress().getCountry(),
                employeeDTO.getAddress().getCity());

        address.ifPresent(employee::setAddress);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        ModelMapper mapper = new ModelMapper();
        return this.employeeRepository.findAll()
                .stream()
                .map(e->mapper.map(e,EmployeeDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public EmployeeNamesDTO findNamesById(long id) {
        return this.employeeRepository.findNamesById(id);
    }

    @Override
    public EmployeeNameAndSalaryDTO findNameAndSalaryById(long id) {
        return this.employeeRepository.findFirstNameAndSalaryById(id);
    }
}
