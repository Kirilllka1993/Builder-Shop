package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.*;

import java.util.List;

public interface GoodService {
    int addGood(GoodDto goodDto) throws GoodException, RepeatitionException;//выполнено

    GoodDto findByNameGood(String goodName) throws GoodNotFoundException;//выполнено

    List<GoodDto> findAllGoods();//пользователь

    List<GoodDto> findBySubsection(SubsectionDto subsectionDto) throws SubsectionNotFoundException;//пользователь

    List<GoodDto> findByPurpose(PurposeDto purposeDto) throws PurposeNotFoundException;//пользователь

    void deleteGood(int goodId);//админ

    void changePrice(int goodId, double price) throws GoodException;//админ

    void changeSubsection(int goodId, SubsectionDto subsectionDto) throws SubsectionNotFoundException;//админ

    void changePurpose(int goodId, PurposeDto purposeDto) throws PurposeNotFoundException; //админ

    void changeUnit(int goodId, String unit);//админ

    void changeQuantity(int goodId, int quantity);//админ

    void changeAmount(int goodId, int amount) throws GoodException;//админ

    void updateGood(int goodId, GoodDto goodDto) throws GoodException,GoodNotFoundException;//админ
    
    List<GoodDto> findGoodsByPrice(double minPrice, double maxPrice);//админ

    GoodDto findGoodById(int goodId) throws GoodNotFoundException;//админ

    void reduceAmount(int goodId, int amount);

}
