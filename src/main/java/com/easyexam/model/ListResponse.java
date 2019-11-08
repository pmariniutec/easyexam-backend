package com.easyexam.model;

import java.util.List;

public class ListResponse {

  private int statusCode;
  private List data;
  private String message;

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public List getData() {
    return data;
  }

  public void setData(List data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
