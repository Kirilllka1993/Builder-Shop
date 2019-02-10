package com.vironit.kazimirov.entity.builder.Review;

import com.vironit.kazimirov.entity.Review;

public class ReviewBuilder {
    private Review review;

    public ReviewBuilder() {
        review = new Review();
    }

    public ReviewBuilder withMark(int mark) {
        review.setMark(mark);
        return this;
    }


    public ReviewBuilder withText(String text) {
        review.setText(text);
        return this;
    }

    public Review build() {
        return review;
    }
}
