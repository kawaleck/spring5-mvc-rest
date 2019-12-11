package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.exceptions.ResourceNotFoundException;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    public static final String CUSTOMER_URL = "/api/v1/customer/";
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(CUSTOMER_URL + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByFirstameAndLastname(String firstname, String lastname) {

        Customer customer = customerRepository
                .findByFirstnameAndLastname(firstname, lastname);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerUrl(CUSTOMER_URL + customer.getId());
        return customerDTO;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
                    customerDTO.setCustomerUrl(CUSTOMER_URL + customer.getId());
                    return customerDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

        return saveAndReturnCustomerDTO(customerMapper.customerDtoToCustomer(customerDTO));

    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {

        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnCustomerDTO(customer);
    }

    private CustomerDTO saveAndReturnCustomerDTO(Customer customer) {

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnDto.setCustomerUrl(CUSTOMER_URL + savedCustomer.getId());

        return returnDto;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        return customerRepository.findById(id)
                .map(customer -> {
                    if(customerDTO.getFirstname() != null) {
                        customer.setFirstname(customerDTO.getFirstname());
                    }
                    if(customerDTO.getLastname() != null) {
                        customer.setLastname(customerDTO.getLastname());
                    }
                    return saveAndReturnCustomerDTO(customer);
                }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {

        customerRepository.deleteById(id);
    }
}
