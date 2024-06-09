package com.example.wsdltest.service;

import com.example.wsdltest.pojo.User;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebService(serviceName = "UserService",
targetNamespace = "service.wsdltest.example.com",
endpointInterface = "com.example.wsdltest.service.IUserService")
@Service
public class UserServiceImpl implements IUserService {

    private static List<User> users = new ArrayList<User>();

    public UserServiceImpl() {
        users.clear();
        users.add(new User(1, "admin", "123456"));
    }
    @Override
    public String echo(String msg) {
        return "Server says: " + msg;
    }

    @Override
    public int add(int a, int b) {
        System.out.printf("a+b=%d\n", a+b);
        return a + b;
    }

    @Override
    public User addUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        for (User user : users) {
            if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> list(String authInfo) {
        System.out.println(authInfo);
        return users;
    }
}
