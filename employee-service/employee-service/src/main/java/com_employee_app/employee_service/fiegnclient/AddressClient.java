package com_employee_app.employee_service.fiegnclient;

import com_employee_app.employee_service.controller.AddressRequest;
import com_employee_app.employee_service.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "address-service",path = "/address-service/api" )
public interface AddressClient {
    // Your code here
    @GetMapping("/address/{id}")
    ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("id") int id);
    @PostMapping("/saveaddress")
    public ResponseEntity<AddressResponse> saveAddress(@RequestBody AddressRequest addressRequest);
}
