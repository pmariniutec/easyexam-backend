package com.easyexam.message.request;

import java.util.Set;
import javax.validation.constraints.*;

public class UpdateUserForm {
  @Size(min = 3, max = 50)
  private String firstName;

  @Size(min = 3, max = 50)
  private String lastName;

  @Size(max = 60)
  @Email
  private String email;

  private Set<String> role;

  @Size(min = 6, max = 40)
  private String password;

  private int points;

  public void setPoints(int points){
      this.points = points;
  }
  public int getPoints(){
      return this.points;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRole() {
    return this.role;
  }

  public void setRole(Set<String> role) {
    this.role = role;
  }
}
