package com.ecommerce.project.controller;

import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.AddressResponse;
import com.ecommerce.project.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Create address", description = "Create and add address to the current authenticated user")
    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.createAddress(addressDTO), HttpStatus.CREATED);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Get all addresses", description = "Get all available addresses")
    @GetMapping("/addresses")
    public ResponseEntity<AddressResponse> getAllAddresses() {
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Get address by ID", description = "Get the address based on its ID")
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@Parameter(description = "ID of the address you wish to get") @PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Get addresses by user", description = "Get all addresses associated with the current authenticated user")
    @GetMapping("/users/addresses")
    public ResponseEntity<AddressResponse> getAddressesByUser() {
        return new ResponseEntity<>(addressService.getAddressesByUser(), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Update address", description = "Update the existing address")
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@Parameter(description = "ID of the address you wish to update") @PathVariable Long addressId,
                                                        @Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.updateAddress(addressId, addressDTO), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Update user address", description = "Update the address for the current authenticated user")
    @PutMapping("/users/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateUserAddress(@Parameter(description = "ID of the address you wish to update") @PathVariable Long addressId,
                                                    @Valid @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.updateUserAddress(addressId, addressDTO), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Delete address", description = "Delete address")
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddress(@Parameter(description = "ID of the address you wish to delete") @PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.deleteUserAddress(addressId), HttpStatus.OK);
    }

    @Tag(name = "Address APIs", description = "APIs to manage addresses")
    @Operation(summary = "Delete user address", description = "Delete the address associated with the current authenticated user")
    @DeleteMapping("/users/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteUserAddress(@Parameter(description = "ID of the address you wish to delete") @PathVariable Long addressId) {
        return new ResponseEntity<>(addressService.deleteUserAddress(addressId), HttpStatus.OK);
    }
}
