package com.sbzze.travelfriend.controller;


import com.sbzze.travelfriend.entity.User;
import com.sbzze.travelfriend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 增
    @PostMapping( value = "/insert")
    public Object insert( @RequestBody User user ) {
        return userService.insertUser( user );
    }

    // 改
    @PostMapping( value = "/update")
    public Object update( @RequestBody User user ) {
        return userService.updateUser( user );
    }

    // 删
    @PostMapping( value = "/delete")
    public Object delete( @RequestBody User user ) {
        return userService.deleteUser( user );
    }

    // 查
    @GetMapping( value = "/getUserByName")
    public Object getUserByName( @RequestParam String userName ) {
        return userService.findUserByName( userName );
    }
}
