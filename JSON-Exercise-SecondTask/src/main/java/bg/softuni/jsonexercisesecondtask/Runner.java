package bg.softuni.jsonexercisesecondtask;

import bg.softuni.jsonexercisesecondtask.services.car.CarService;
import bg.softuni.jsonexercisesecondtask.services.customer.CustomerService;
import bg.softuni.jsonexercisesecondtask.services.sale.SaleService;
import bg.softuni.jsonexercisesecondtask.services.seed.SeedService;
import bg.softuni.jsonexercisesecondtask.services.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;

    private CustomerService customerService;
    private CarService carService;

    private SupplierService supplierService;

    private SaleService saleService;
    @Autowired
    public Runner(SeedService seedService, CustomerService customerService, CarService carService, SupplierService supplierService, SaleService saleService) {
        this.seedService = seedService;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAllInputIntoDataBase();
//        this.customerService.getAllCustomersOrderByBirthdate();
//        this.carService.getAllCarsMadeFromToyota("Toyota");
//        this.supplierService.getAllSuppliersNotImportingAbroad();
//        this.carService.getAllCarsWithInfoForTheParts();
//        this.customerService.getAllCustomerWithSpentMoney();
        this.saleService.getAllSales();
    }
}
