package com.ecommerce.project.controller;

import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.AddressResponse;
import com.ecommerce.project.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.createAddress(addressDTO), HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressResponse> getAllAddresses() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<AddressResponse> getAddressesByUser() {
        return new ResponseEntity<>(addressService.getAddressesByUser(), HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                        @Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.updateAddress(addressId, addressDTO), HttpStatus.OK);
    }

    @PutMapping("/users/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateUserAddress(@PathVariable Long addressId,
                                                    @Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.updateUserAddress(addressId, addressDTO), HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.deleteUserAddress(addressId), HttpStatus.OK);
    }

    @DeleteMapping("/users/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteUserAddress(@PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.deleteUserAddress(addressId), HttpStatus.OK);
    }
}
