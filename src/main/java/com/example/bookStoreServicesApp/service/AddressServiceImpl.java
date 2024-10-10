package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.AddressNotFoundException;
import com.example.bookStoreServicesApp.exception.NotBelongToUserException;
import com.example.bookStoreServicesApp.exception.UserNotFoundException;
import com.example.bookStoreServicesApp.model.Address;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.repository.AddressRepository;
import com.example.bookStoreServicesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;


    @Override
    public void save(Address address, Long id) throws UserNotFoundException
    {
        User user=userRepository.findById(id).get();
        Address address1=new Address();
        address1.setUser(user);
        address1.setId(address.getId());
        address1.setHouseNo(address1.getHouseNo());
        address1.setStreet(address.getStreet());
        address1.setCity(address.getCity());
        address1.setPostalCode(address.getPostalCode());
        addressRepository.save(address1);
    }

    public void deleteAddressById(Long userId, Long addressId) throws AddressNotFoundException, UserNotFoundException, NotBelongToUserException {
        Optional<Address> byId = addressRepository.findById(addressId);
        if(byId.isPresent())
        {
            Address address=byId.get();
            if(address.getUser().getId().equals(userId))
            {
                addressRepository.deleteById(addressId);
            }
            else {
                throw new NotBelongToUserException("address not belong to user");
            }

        }
        else {
            throw new AddressNotFoundException("address id is not valid");
        }

    }


//    public void updateAddressById(Long userId, Address address) throws UserNotFoundException {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            List<Address> addressList = user.getAddressList();
//            for (int i = 0; i < addressList.size(); i++) {
//                Address address1 = addressList.get(i);
//                if (address1.getId().equals(address.getId())) {
//                    address1.setHouseNo(address.getHouseNo());
//                    address1.setCity(address.getCity());
//                    address1.setStreet(address.getStreet());
//                    address1.setState(address.getState());
//                    address1.setPostalCode(address1.getPostalCode());
//                    address.
//                    addressRepository.save(address1);
//                }
//            }
//        } else {
//            throw new UserNotFoundException("User not found with id: " + userId);
//        }
//    }

    @Override
    public List<Address> findAll(Long userId)
    {
        return addressRepository.findByUserId(userId);
    }


}

