package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/api")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> findAll() {
        return userDao.findAll();
    }

    @RequestMapping(value = "/users/id/{username}", method = RequestMethod.GET)
    public int findIdByUsername(@PathVariable String username) {
        return userDao.findIdByUsername(username);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public User findByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }

}
