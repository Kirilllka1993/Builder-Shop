package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeService {

    int addPurpose(PurposeDto purposeDto) throws RepeatitionException;

    List<PurposeDto> findPurposes();

    PurposeDto findPurposeByName(String purposeName) throws PurposeNotFoundException;

    void deletePurpose(int purposeId) throws CantDeleteElement, PurposeNotFoundException;

    PurposeDto findPurposeById(int purposeId) throws PurposeNotFoundException;

}
