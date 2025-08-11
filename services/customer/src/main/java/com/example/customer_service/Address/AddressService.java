package com.example.customer_service.Address;

import com.example.customer_service.Address.mapper.AddressMapper;
import com.example.customer_service.Address.modal.request.AddressRequest;
import com.example.customer_service.Address.modal.response.AddressDTO;
import com.example.customer_service.Customer.Customer;
import com.example.customer_service.Customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;

    public ResponseEntity<Object> addAddress(AddressRequest request, String userId) {
        Address address = addressMapper.toAddress(request);
        Customer customer = customerRepository.findById(userId).orElseThrow();
        address.setCustomer(customer);
//        if(!request.getId().isEmpty()){
//            address.setId(request.getId());
//            addressRepository.save(address);
//            return ResponseEntity.ok("Sửa thành công");
//        }
        addressRepository.save(address);
        return ResponseEntity.ok("Tạo thành công!");
    }

    public ResponseEntity<Object> deleteAddress(String addressId) {
        addressRepository.deleteById(addressId);
        return ResponseEntity.ok("Xoá thành công!");
    }

    public ResponseEntity<Object> getAddressAll(String userId) {
        Optional<Address> address = addressRepository.findById(userId);
        List<AddressDTO> addressDTOList = address.stream().map(addressMapper::toAddressDTO).toList();
        return ResponseEntity.ok(addressDTOList);
    }

    public AddressDTO getAddress(String addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow();
        return addressMapper.toAddressDTO(address);
    }
}
