package com.ecommerce.project.payload;


import java.util.List;

public class AddressResponse {
    private List<AddressDTO> addresses;

    public AddressResponse() {
    }

    public AddressResponse(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
