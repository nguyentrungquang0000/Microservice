package com.example.customer_service.Address.mapper;

import com.example.customer_service.Address.Address;
import com.example.customer_service.Address.modal.request.AddressRequest;
import com.example.customer_service.Address.modal.response.AddressDTO;
import org.springframework.stereotype.Service;

@Service
public class AddressMapper {
    public Address toAddress(AddressRequest request) {
        Address address = new Address();
        address.setName(request.getName());
        address.setPhone(request.getPhone());
        address.setCity(request.getCity());
        address.setWard(request.getWard());
        address.setAddress(request.getAddress());
        return address;
    }

    public AddressDTO toAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setName(address.getName());
        addressDTO.setPhone(address.getPhone());
        addressDTO.setCity(address.getCity());
        addressDTO.setWard(address.getWard());
        addressDTO.setAddress(address.getAddress());
        addressDTO.setCustomerId(address.getCustomer().getId());
        return addressDTO;
    }
}
