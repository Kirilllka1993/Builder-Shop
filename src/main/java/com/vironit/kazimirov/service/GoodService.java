package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.*;

import java.util.List;

public interface GoodService {
    int addGood(GoodDto goodDto) throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException;

    GoodDto findByNameGood(String goodName) throws GoodNotFoundException;

    List<GoodDto> findAllGoods();

    List<GoodDto> findBySubsection(SubsectionDto subsectionDto) throws SubsectionNotFoundException;

    List<GoodDto> findByPurpose(PurposeDto purposeDto) throws PurposeNotFoundException;

    void deleteGood(int goodId) throws GoodNotFoundException;

    void changePrice(int goodId, double price) throws GoodException, GoodNotFoundException;

    void changeSubsection(int goodId, SubsectionDto subsectionDto) throws SubsectionNotFoundException, GoodNotFoundException;

    void changePurpose(int goodId, PurposeDto purposeDto) throws PurposeNotFoundException, GoodNotFoundException;

    void changeUnit(int goodId, String unit) throws GoodNotFoundException;

    void changeQuantity(int goodId, int quantity) throws GoodNotFoundException;

    void changeAmount(int goodId, int amount) throws GoodException, GoodNotFoundException;

    void updateGood(int goodId, GoodDto goodDto) throws GoodException,GoodNotFoundException;
    
    List<GoodDto> findGoodsByPrice(double minPrice, double maxPrice);

    GoodDto findGoodById(int goodId) throws GoodNotFoundException;

    void reduceAmount(int goodId, int amount);

}
