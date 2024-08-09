package com_employee_app.employee_service.controller.service;

import com_employee_app.employee_service.controller.AddressRequest;
import com_employee_app.employee_service.controller.EmployeeRequest;
import com_employee_app.employee_service.entitites.Employee;
import com_employee_app.employee_service.fiegnclient.AddressClient;
import com_employee_app.employee_service.repo.EmployeeRepository;
import com_employee_app.employee_service.response.AddressResponse;
import com_employee_app.employee_service.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    // Your code here
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;


    @Autowired
    private AddressClient addressClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public EmployeeResponse getEmployee(int id) {
        LOGGER.info("Fetching employee with id: " + id);
        Employee employee = employeeRepository.findById(id).orElseThrow();
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        //AddressResponse addressResponse = getAddressResponseViaRestTemplate(id);
        //AddressResponse addressResponse = getAddressResponseViaWebClient(id);
        AddressResponse addressResponse = addressClient.getAddressByEmployeeId(id).getBody();
        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }

    public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
        LOGGER.info("Saving employee");
        Employee employee = modelMapper.map(employeeRequest, Employee.class);
        AddressResponse addressResponse = addressClient.saveAddress(employeeRequest.getAddressRequest()).getBody();
        EmployeeResponse employeeResponse = modelMapper.map(employeeRepository.save(employee), EmployeeResponse.class);
        employeeResponse.setAddressResponse(addressResponse);
        return employeeResponse;
    }

    public List<EmployeeResponse> getEmployees() {
        LOGGER.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employee -> {
                    AddressResponse addressResponse = addressClient.getAddressByEmployeeId(employee.getId()).getBody();
                    EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
                    employeeResponse.setAddressResponse(addressResponse);
                    return employeeResponse;
                })
                .collect(Collectors.toList());
    }

    private AddressResponse getAddressResponseViaWebClient(int id) {

        AddressResponse addressResponse = webClient.get().uri("/address-service/api/address/{id}", id).retrieve().bodyToMono(AddressResponse.class).block();
        return addressResponse;
    }

    private AddressResponse getAddressResponseViaRestTemplate(int id) {

        ServiceInstance baseurlViaLoadBalancerClient = getBaseurlViaLoadBalancerClient();
        String baseUrl = baseurlViaLoadBalancerClient.getUri().toString();
        String contextPath = baseurlViaLoadBalancerClient.getMetadata().get("configPath");
        LOGGER.info("Base url: " + baseUrl);
        LOGGER.info("Context path: " + contextPath);
        AddressResponse addressResponse = restTemplate.getForEntity("http://ADDRESS-SERVICE" + contextPath + "/address/{id}", AddressResponse.class, id).getBody();
        return addressResponse;
    }

    private String getBaseurlViaDiscoveryClient() {
        discoveryClient.getInstances("address-service").forEach(System.out::println);
        String baseUrl = discoveryClient.getInstances("address-service").get(0).getUri().toString();
        LOGGER.info("Base url: " + baseUrl);
        return baseUrl;
    }

    private ServiceInstance getBaseurlViaLoadBalancerClient() {
        return loadBalancerClient.choose("address-service");


    }


}
