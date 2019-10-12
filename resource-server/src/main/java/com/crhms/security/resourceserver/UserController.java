package com.crhms.security.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private Resource2Service resource2Service;

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("This is a message from resouce server1 !");

        String hello = resource2Service.hello();

        return "This is a message from resouce server1 !   " + hello;
    }
}
