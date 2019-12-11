package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/api/v1/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("customers")
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(customerService.getAllCustomers());
    }

    @GetMapping("customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("customers/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO getCustomerById(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @GetMapping("customer")
    public CustomerDTO getCustomerByFirstnameAndLastName(@RequestParam String firstname,
                                                         @RequestParam String lastname) {
        return customerService.getCustomerByFirstameAndLastname(firstname, lastname);
    }

    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PatchMapping("customers/{id}")
    public CustomerDTO patchCustomerById(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.patchCustomer(id, customerDTO);
    }

    @DeleteMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
