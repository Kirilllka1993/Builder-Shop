package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dao.PurposeDaoImpl;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.service.PurposeService;

import java.util.List;

public class PurposeServiceImpl implements PurposeService {
    @Override
    public void addPurpose(String title) {
        PurposeDaoImpl purposeDaoImpl=new PurposeDaoImpl();
        purposeDaoImpl.addPurpose(title);

    }

    @Override
    public List<Purpose> showPurposes() {
        return null;

    }
}
