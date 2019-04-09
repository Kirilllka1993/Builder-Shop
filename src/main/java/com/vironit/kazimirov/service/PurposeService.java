package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeService {

    int addPurpose(PurposeDto purposeDto) throws RepeatitionException;//админ

    List<PurposeDto> findPurposes();//пользователь

    PurposeDto findPurposeByName(String purposeName) throws PurposeNotFoundException;//пользователь

    void deletePurpose(int purposeId) throws CantDeleteElement;//алдмин

    PurposeDto findPurposeById(int purposeId) throws PurposeNotFoundException;//админ

}
