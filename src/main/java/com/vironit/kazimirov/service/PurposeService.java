package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeService {

    void addPurpose(Purpose purpose) throws RepeatitionException;

    List<Purpose> findPurposes();

    Purpose findPurposeByName(String purposeName);

    void deletePurpose(int idPurpose) throws CantDeleteElement;

    Purpose findPurposeById(int idPurpose);

}
