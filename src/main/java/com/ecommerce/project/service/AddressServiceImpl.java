package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.AddressResponse;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.utils.AuthUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AuthUtils authUtils;

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AuthUtils authUtils,
                              AddressRepository addressRepository) {
        this.authUtils = authUtils;
        this.addressRepository = addressRepository;
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = AddressDTO.toEntity(addressDTO);
        User user = authUtils.loggedInUser();

        address.setUser(user);

        addressRepository.save(address);
        return AddressDTO.fromEntity(address);
    }

    @Override
    public AddressResponse getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new APIException("There is no address");
        }
        List<AddressDTO> addressDTOS = addresses.stream()
                .map(address -> AddressDTO.fromEntity(address)).toList();
        return new AddressResponse(addressDTOS);
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException("Address", "addressId", addressId);
        }
        return AddressDTO.fromEntity(addressOptional.get());
    }

    @Override
    public AddressResponse getAddressesByUser() {
        // This approach is poor in clarity, as it ignores the existing relationship of Address and User in ORM
        /*
        List<Address> addresses = addressRepository.findAllByEmail(authUtils.loggedInEmail());
        if (addresses.isEmpty()) {
            throw new APIException("This user has no address");
        }
         */

        // This make use of the object graph navigation: "This user has addresses", because we only access addresses of one user
        // no N+1 problem occurs
        List<Address> addresses = authUtils.loggedInUser().getAddresses();
        List<AddressDTO> addressDTOS = addresses.stream()
                .map(address -> AddressDTO.fromEntity(address)).toList();
        return new AddressResponse(addressDTOS);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {

        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Address addressFromDb = addressOptional.get();
        User user = addressFromDb.getUser();

        addressFromDb.setStreet(addressDTO.getStreet());
        addressFromDb.setBuilding(addressDTO.getBuilding());
        addressFromDb.setState(addressDTO.getState());
        addressFromDb.setCountry(addressDTO.getCountry());
        addressFromDb.setZipcode(addressDTO.getZipcode());
        Address updatedAddress = addressRepository.save(addressFromDb);




        boolean isRemoved = user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        if (isRemoved) {
            user.setAddress(updatedAddress);
        }
        return AddressDTO.fromEntity(updatedAddress);
    }

    @Override
    public AddressDTO updateUserAddress(Long addressId, AddressDTO addressDTO) {
        // Here we fetch only one resource that match the addressId and also belongs to the current user
        // So a custom query like this is the best practice
        User currentUser = authUtils.loggedInUser();
        Optional<Address> addressOptional = addressRepository.findByIdAndEmail(addressId, currentUser.getEmail());
        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Address addressFromDb = addressOptional.get();
        addressFromDb.setStreet(addressDTO.getStreet());
        addressFromDb.setBuilding(addressDTO.getBuilding());
        addressFromDb.setState(addressDTO.getState());
        addressFromDb.setCountry(addressDTO.getCountry());
        addressFromDb.setZipcode(addressDTO.getZipcode());
        Address updatedAddress = addressRepository.save(addressFromDb);

        currentUser.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        currentUser.setAddress(updatedAddress);

        return AddressDTO.fromEntity(updatedAddress);
    }

    @Override
    public AddressDTO deleteAddress(Long addressId) {

        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Address address = addressOptional.get();
        User user = address.getUser();
        addressRepository.delete(address);

        user.getAddresses().removeIf(a -> a.getAddressId().equals(addressId));

        return AddressDTO.fromEntity(address);
    }

    @Override
    public AddressDTO deleteUserAddress(Long addressId) {
        User currentUser = authUtils.loggedInUser();
        Optional<Address> addressOptional = addressRepository.findByIdAndEmail(addressId, authUtils.loggedInEmail());
        if (addressOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        Address address = addressOptional.get();
        addressRepository.delete(address);

        currentUser.getAddresses().removeIf(a -> a.getAddressId().equals(addressId));


        return AddressDTO.fromEntity(address);
    }
}
