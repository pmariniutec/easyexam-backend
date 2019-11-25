package com.easyexam.message.request;

import javax.validation.constraints.*;
import com.easyexam.model.Rating;

public class RatingAddForm {

    
    private Rating rating;
    
    public void setRating(Rating rating){
        this.rating = rating;
    }

    public Rating getRating(){
        return this.rating;
    }

}
