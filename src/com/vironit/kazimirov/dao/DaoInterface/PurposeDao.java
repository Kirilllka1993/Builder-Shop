package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;

import java.util.List;

public interface PurposeDao {

    void addPurpose(String title);

    List<Purpose> showPurposes();

}
