package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerByFirstameAndLastname(String firstname, String lastname);

    CustomerDTO getCustomerById(Long id);

}
