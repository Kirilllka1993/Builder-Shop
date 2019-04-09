package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.*;

import java.util.List;

public interface GoodService {
    int addGood(GoodDto goodDto) throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException;//выполнено

    GoodDto findByNameGood(String goodName) throws GoodNotFoundException;//выполнено

    List<GoodDto> findAllGoods();//выполнено

    List<GoodDto> findBySubsection(SubsectionDto subsectionDto) throws SubsectionNotFoundException;//выполнено

    List<GoodDto> findByPurpose(PurposeDto purposeDto) throws PurposeNotFoundException;//выполнено

    void deleteGood(int goodId) throws GoodNotFoundException;//выполнено

    void changePrice(int goodId, double price) throws GoodException, GoodNotFoundException;//выполнено
    void changeSubsection(int goodId, SubsectionDto subsectionDto) throws SubsectionNotFoundException, GoodNotFoundException;//выполнено

    void changePurpose(int goodId, PurposeDto purposeDto) throws PurposeNotFoundException, GoodNotFoundException; //выполнено

    void changeUnit(int goodId, String unit) throws GoodNotFoundException;//выполнено

    void changeQuantity(int goodId, int quantity) throws GoodNotFoundException;//выполнено

    void changeAmount(int goodId, int amount) throws GoodException, GoodNotFoundException;//выполнено

    void updateGood(int goodId, GoodDto goodDto) throws GoodException,GoodNotFoundException;//
    
    List<GoodDto> findGoodsByPrice(double minPrice, double maxPrice);//выполнено

    GoodDto findGoodById(int goodId) throws GoodNotFoundException;//выполнено

    void reduceAmount(int goodId, int amount);

}
