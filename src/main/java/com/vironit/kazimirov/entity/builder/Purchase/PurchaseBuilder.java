package com.vironit.kazimirov.entity.builder.Purchase;

import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PurchaseBuilder {
    private Purchase purchase;

    public PurchaseBuilder() {
        purchase = new Purchase();
    }

    public PurchaseBuilder withCost(double cost) {
        purchase.setSum(cost);
        return this;
    }


//    public PurchaseBuilder withGoods(List<Good> goods) {
//        purchase.setGoods(goods);
//        return this;
//    }

    public PurchaseBuilder withClient(User user) {
        purchase.setUser(user);
        return this;
    }

    public PurchaseBuilder withRegistration(LocalDateTime registration) {
        purchase.setRegistration(registration);
        return this;
    }

    public PurchaseBuilder withPurchase(LocalDateTime purchase1) {
        purchase.setTimeOfPurchase(purchase1);

        return this;
    }

    public PurchaseBuilder withStatus(Status status) {
        purchase.setStatus(status);
        return this;
    }


    public Purchase build() {
        return purchase;
    }
}
