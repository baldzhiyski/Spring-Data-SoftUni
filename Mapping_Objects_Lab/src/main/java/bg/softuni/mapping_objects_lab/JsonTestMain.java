package bg.softuni.mapping_objects_lab;

import bg.softuni.mapping_objects_lab.entities.dtos.AddressDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.CompanyDTO;
import bg.softuni.mapping_objects_lab.entities.dtos.CreateEmployeeDTO;
import com.google.gson.*;
import org.springframework.boot.CommandLineRunner;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class JsonTestMain implements CommandLineRunner {

    private final Scanner scanner;
    private final Gson gson;

    public JsonTestMain(Scanner scanner, Gson gson) {
        this.scanner = scanner;

        this.gson = gson;
    }

    public static class LocalDateAdapter implements JsonSerializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }
    }

    @Override
    public void run(String... args) throws Exception {
        test2();
    }

    private void test2() {
        AddressDTO address1 = new AddressDTO("Bulgaria", "Burgas");
        CreateEmployeeDTO employee1 = new CreateEmployeeDTO(
                "First", "Last", BigDecimal.ONE, LocalDate.now(), address1
        );

        AddressDTO address2 = new AddressDTO("Bulgaria", "Varna");
        CreateEmployeeDTO employee2 = new CreateEmployeeDTO(
                "Second", "Last", BigDecimal.TEN, LocalDate.now(), address2
        );

        AddressDTO address3 = new AddressDTO("Bulgaria", "Ruse");
        CreateEmployeeDTO employee3 = new CreateEmployeeDTO(
                "Third", "Last", BigDecimal.ZERO, LocalDate.now(), address3
        );

        CompanyDTO mega = new CompanyDTO("Mega", Set.of(employee1, employee2, employee3));

        System.out.println(this.gson.toJson(mega));

        String input = this.scanner.nextLine();

        CompanyDTO parsed = this.gson.fromJson(input, CompanyDTO.class);

        Set<CreateEmployeeDTO> employees = parsed.getEmployees();

        for (CreateEmployeeDTO employee : employees) {
            System.out.println(employee.getAddress().getCity());
        }
    }

    private void test1() {
        AddressDTO address1 = new AddressDTO("Bulgaria", "Burgas");
        AddressDTO address2 = new AddressDTO("Bulgaria", "Varna");
        AddressDTO address3 = new AddressDTO("Bulgaria", "Ruse");

        CreateEmployeeDTO createEmployeeDTO = new CreateEmployeeDTO(
                "First", "Last", BigDecimal.ONE, LocalDate.now(), address1
        );

        String json = gson.toJson(createEmployeeDTO);
        System.out.println(json);

        List<AddressDTO> addressList = List.of(address1, address2, address3);
        System.out.println(gson.toJson(addressList));


        String input = this.scanner.nextLine();

        CreateEmployeeDTO parsedDTO = gson.fromJson(input, CreateEmployeeDTO.class);
//        CreateEmployeeDTO[] list = gson.fromJson(input, CreateEmployeeDTO[].class);

        System.out.println();
    }
}
