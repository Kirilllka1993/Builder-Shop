package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface PurposeService {
    void addPurpose(String title);

    List<Purpose> showPurposes();
}
