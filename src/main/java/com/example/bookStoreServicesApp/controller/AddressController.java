package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.AccessDeniedException;
import com.example.bookStoreServicesApp.exception.AddressNotFoundException;
import com.example.bookStoreServicesApp.exception.NotBelongToUserException;
import com.example.bookStoreServicesApp.exception.UserNotFoundException;
import com.example.bookStoreServicesApp.exception.*;
import com.example.bookStoreServicesApp.model.Address;
import com.example.bookStoreServicesApp.service.AddressService;
import com.example.bookStoreServicesApp.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class AddressController
{
    @Autowired
    JwtService jwtService;
    @Autowired
    AddressService addressService;
    @PostMapping("/addAddress")
    public void save(@RequestBody Address address,@RequestHeader("Authorization") String token) throws UserNotFoundException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);
        if(role.equalsIgnoreCase("CUSTOMER")){
            addressService.save(address,User_id);
        }
        else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }
    @DeleteMapping("deleteAddress/{id}")
    public void deleteById(@PathVariable("id") Long addressId,@RequestHeader("Authorization") String token) throws AddressNotFoundException, UserNotFoundException, NotBelongToUserException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);
        if(role.equalsIgnoreCase("CUSTOMER")){
            addressService.deleteAddressById(User_id,addressId);
        }
        else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }
    @GetMapping("/getAll")
    public List<Address> getAll(@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);
        if(role.equalsIgnoreCase("CUSTOMER")){
            return addressService.findAll(User_id);
        }
        else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

}
