package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Purpose;

import java.util.List;

public interface PurposeService {
    void addPurpose(String title);

    List<Purpose> showPurposes();
}
