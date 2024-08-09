package com.address__app.address_service.service;

import com.address__app.address_service.AddressRepo;
import com.address__app.address_service.controller.AddressRequest;
import com.address__app.address_service.pojo.Address;
import com.address__app.address_service.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    Logger LOGGER= org.slf4j.LoggerFactory.getLogger(AddressService.class);
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private ModelMapper modelMapper;

    public AddressResponse getAddressByEmployeeId(int id) {
        LOGGER.info("Fetching address for employee id: {}", id);
        Address address = addressRepo.findByEmployeeId(id);

        return modelMapper.map(address, AddressResponse.class);
    }

    public AddressResponse saveAddress(AddressRequest addressRequest) {
        LOGGER.info("Saving address for employee id: {}", addressRequest.getId());
        Address address = modelMapper.map(addressRequest, Address.class);
        addressRepo.save(address);
        return modelMapper.map(address, AddressResponse.class);
    }
}
