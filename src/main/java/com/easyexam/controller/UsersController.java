package com.easyexam.controller;

import com.easyexam.model.ListResponse;
import com.easyexam.model.ObjectResponse;
import com.easyexam.model.User;
import com.easyexam.repository.UserRepository;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/users")
public class UsersController {

  @Autowired private UserRepository userRepository;

  @RequestMapping(method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('ADMIN_USER')")
  public ListResponse getAllUsers(HttpServletResponse http) {
    ListResponse response = new ListResponse();
    response.setMessage("Successfully Retrieved");
    response.setStatusCode(http.getStatus());
    List<User> users = userRepository.findAll();
    response.setData(users);
    return response;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ListResponse createUser(@RequestBody final User user, HttpServletResponse http) {
    userRepository.save(user);
    ListResponse response = new ListResponse();
    response.setMessage("Successfully Created");
    response.setStatusCode(http.getStatus());
    List<User> users = userRepository.findAll();
    response.setData(users);
    return response;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ObjectResponse getUser(@PathVariable("id") Integer userId, HttpServletResponse http) {
    ObjectResponse response = new ObjectResponse();
    if (userRepository.exists(userId)) {
      response.setMessage("Successfully Retrieved");
      response.setStatusCode(http.getStatus());
      response.setData(userRepository.findOne(userId));
    } else {
      response.setMessage("Record not found");
      response.setStatusCode(404);
      response.setData(null);
    }
    return response;
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ObjectResponse updateUser(@RequestBody final User user, HttpServletResponse http) {
    ObjectResponse response = new ObjectResponse();
    if (userRepository.exists(user.getId())) {
      userRepository.updateUser(user.getName(), user.getEmail(), user.getId());
      response.setMessage("Successfully Updated");
      response.setStatusCode(http.getStatus());
      response.setData(userRepository.findOne(user.getId()));
    } else {
      response.setMessage("Record not found");
      response.setStatusCode(404);
      response.setData(null);
    }
    return response;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ListResponse deleteUser(@PathVariable("id") Integer userId, HttpServletResponse http) {
    ListResponse response = new ListResponse();
    if (userRepository.exists(userId)) {
      userRepository.delete(userId);
      response.setStatusCode(http.getStatus());
      response.setMessage("Successfully Deleted");
    } else {
      response.setStatusCode(404);
      response.setMessage("Record not found");
    }
    List<User> users = userRepository.findAll();
    response.setData(users);
    return response;
  }
}
