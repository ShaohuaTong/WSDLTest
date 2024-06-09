package com.example.wsdltest.service;

import com.example.wsdltest.pojo.User;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

import java.util.List;

@WebService(name = "UserService", targetNamespace = "service.wsdltest.example.com")
public interface IUserService {
    @WebResult(name = "echoResult")
    String echo(@WebParam(name = "msg") String msg);

    @WebResult(name = "addResult")
    int add(@WebParam(name = "a") int a, @WebParam(name = "b") int b);

    @WebResult(name="user")
    User addUser(@WebParam(name = "user") User user);

    @WebResult(name = "user")
    User login(@WebParam(name = "username") String username, @WebParam(name = "password") String password);

    @WebResult(name = "user")
    List<User> list(@WebParam(header = true, name = "authInfo") String authInfo);
}
