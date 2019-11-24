
package com.easyexam.message.response;

public class SuccessfulCreation {
    private Long id;
    private String message;

    public SuccessfulCreation(Long id, String type) {
        this.id = id;
        this.message = "Successfully created " + type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getmessage() {
        return this.message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

}
