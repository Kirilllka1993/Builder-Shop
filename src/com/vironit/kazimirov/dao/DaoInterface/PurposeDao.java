package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface PurposeDao {

    void addPurpose(int id, String title);

    List<Subsection> showPurposes();

}
