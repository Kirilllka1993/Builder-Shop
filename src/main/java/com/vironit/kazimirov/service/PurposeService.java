package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeService {

    int addPurpose(PurposeDto purposeDto) throws RepeatitionException;//выполнено

    List<PurposeDto> findPurposes();//выполнено

    PurposeDto findPurposeByName(String purposeName) throws PurposeNotFoundException;//выполнено

    void deletePurpose(int purposeId) throws CantDeleteElement, PurposeNotFoundException;//выполнено

    PurposeDto findPurposeById(int purposeId) throws PurposeNotFoundException;//выполнено

}
