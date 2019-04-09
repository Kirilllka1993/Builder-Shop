package com.vironit.kazimirov.dto;

import com.vironit.kazimirov.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto  {
    private int id;
    private String comment;
    private int mark;
    private int userId;
    private int goodId;

    public ReviewDto (Review review){
        this.id=review.getId();
        this.comment=review.getComment();
        this.mark=review.getMark();
        this.userId =review.getUser().getId();
        this.goodId=review.getGood().getId();
    }
}
