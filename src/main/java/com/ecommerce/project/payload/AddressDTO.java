package com.ecommerce.project.payload;

import com.ecommerce.project.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddressDTO {

    private Long addressId;

    @NotBlank
    @Size(min = 5, message = "Street name must be at least 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "Building name must be at least 5 characters")
    private String building;

    @NotBlank
    @Size(min = 2, message = "State name must be at least 2 characters")
    private String state;

    @NotBlank
    @Size(min = 2, message = "Country name must be at least 2 characters")
    private String country;

    @NotBlank
    @Size(min = 6, message = "Zipcode must be at least 6 characters")
    private String zipcode;

    public AddressDTO() {
    }

    public AddressDTO(Long addressId, String street, String building, String state, String country, String zipcode) {
        this.addressId = addressId;
        this.street = street;
        this.building = building;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public static AddressDTO fromEntity(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setBuilding(address.getBuilding());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setState(address.getState());
        addressDTO.setZipcode(address.getZipcode());
        addressDTO.setStreet(address.getStreet());

        return addressDTO;
    }

    public static Address toEntity(AddressDTO addressDTO) {
        Address address = new Address();

        address.setAddressId(addressDTO.getAddressId());
        address.setBuilding(addressDTO.getBuilding());
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setZipcode(addressDTO.getZipcode());
        address.setStreet(addressDTO.getStreet());

        return address;
    }
}
