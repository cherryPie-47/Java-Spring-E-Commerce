package com.ecommerce.project.service;

import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.AddressResponse;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);
    AddressResponse getAllAddresses();
    AddressDTO getAddressById(Long addressId);
    AddressResponse getAddressesByUser();
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    AddressDTO updateUserAddress(Long addressId, AddressDTO addressDTO);
    AddressDTO deleteAddress(Long addressId);
    AddressDTO deleteUserAddress(Long addressId);
}
