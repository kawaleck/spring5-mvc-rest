package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Doe";
    public static final String CUSTOMER_URL = "/api/v1/customer/" + ID;

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {

        CustomerDTO customer1 = new CustomerDTO();
        CustomerDTO customer2 = new CustomerDTO();

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);
        customer.setCustomerUrl(CUSTOMER_URL);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)));
    }

    @Test
    public void getCustomerByFirstnameAndLastName() throws Exception {

        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        when(customerService.getCustomerByFirstameAndLastname(anyString(), anyString()))
                .thenReturn(customer);

        mockMvc.perform(get("/api/v1/customer")
                .param("firstname", FIRST_NAME)
                .param("lastname", LAST_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)));
    }

    @Test
    public void createNewCustomer() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);
        customerDTO.setCustomerUrl(CUSTOMER_URL);

        when(customerService.createNewCustomer(ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(customerDTO);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));

    }

    @Test
    public void updateCustomer() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .firstname(FIRST_NAME)
                .lastname(LAST_NAME)
                .customerUrl(CUSTOMER_URL)
                .build();

        when(customerService.updateCustomer(anyLong(), ArgumentMatchers.any(CustomerDTO.class)))
                .thenReturn(customerDTO);

        mockMvc.perform(put("/api/v1/customers/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void patchCustomer() throws Exception {

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);

        CustomerDTO returnDTO = CustomerDTO.builder()
                .firstname(customerDTO.getFirstname())
                .lastname(LAST_NAME)
                .customerUrl(CUSTOMER_URL)
                .build();


        when(customerService.patchCustomer(anyLong(), ArgumentMatchers.any(CustomerDTO.class))). thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CUSTOMER_URL)));
    }

    @Test
    public void deleteCustomer() throws  Exception {

        mockMvc.perform(delete("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService, times(1)).deleteCustomerById(anyLong());
    }
}