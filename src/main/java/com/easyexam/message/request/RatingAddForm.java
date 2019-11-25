package com.easyexam.message.request;

import javax.validation.constraints.*;

public class RatingAddForm {

    
    private Rating rating;
    
    public void setRating(Rating rating){
        this.rating = rating;
    }

    public Rating getRating(){
        return this.rating;
    }

}
