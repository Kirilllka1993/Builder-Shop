package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeService {

    void addPurpose(Purpose purpose) throws RepeatitionException;

    List<Purpose> findPurposes();
}
