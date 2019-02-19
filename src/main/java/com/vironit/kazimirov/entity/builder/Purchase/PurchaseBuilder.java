package com.vironit.kazimirov.entity.builder.Purchase;

import com.vironit.kazimirov.entity.Client;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purchase;
import com.vironit.kazimirov.entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseBuilder {
    private Purchase purchase;

    public PurchaseBuilder() {
        purchase = new Purchase();
    }

    public PurchaseBuilder withCost(double cost) {
        purchase.setCost(cost);
        return this;
    }


    public PurchaseBuilder withGoods(List<Good> goods) {
        purchase.setGoods(goods);
        return this;
    }

    public PurchaseBuilder withClient(Client client) {
        purchase.setClient(client);
        return this;
    }

    public PurchaseBuilder withRegistration(LocalDateTime registration) {
        purchase.setRegistration(registration);
        return this;
    }

    public PurchaseBuilder withPurchase(LocalDateTime purchase1) {
        purchase.setPurchase(purchase1);

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
