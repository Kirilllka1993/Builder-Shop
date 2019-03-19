package com.vironit.kazimirov.entity.builder.Review;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
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
        review.setComment(text);
        return this;
    }

    public ReviewBuilder withClient(Client client) {
        review.setClient(client);
        return this;
    }

    public ReviewBuilder withGood(Good good) {
        review.setGood(good);
        return this;
    }

    public Review build() {
        return review;
    }
}
