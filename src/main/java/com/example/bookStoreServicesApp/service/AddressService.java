package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.AddressNotFoundException;
import com.example.bookStoreServicesApp.exception.NotBelongToUserException;
import com.example.bookStoreServicesApp.exception.UserNotFoundException;
import com.example.bookStoreServicesApp.model.Address;

import java.util.List;

public interface AddressService
{
    public void save(Address Address,Long id) throws UserNotFoundException;
    public void deleteAddressById(Long userId,Long addressId) throws AddressNotFoundException, UserNotFoundException, NotBelongToUserException;
    public List<Address> findAll(Long userId);


}
