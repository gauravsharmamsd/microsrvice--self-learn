package com.address__app.address_service.controller;

import com.address__app.address_service.response.AddressResponse;
import com.address__app.address_service.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressControler {
    private Logger LOGGER = LoggerFactory.getLogger(AddressControler.class);

    @Autowired
    private AddressService addressService;

    @GetMapping("/address/{employeeId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id) {

        return ResponseEntity.status(200).body(addressService.getAddressByEmployeeId(id));
    }

    @PostMapping("/saveaddress")
    public ResponseEntity<AddressResponse> saveAddress(@RequestBody AddressRequest addressRequest) {
        return ResponseEntity.status(201).body(addressService.saveAddress(addressRequest));
    }


}
