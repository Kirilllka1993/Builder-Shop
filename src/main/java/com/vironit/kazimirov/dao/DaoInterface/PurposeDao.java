package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeDao {

    void addPurpose(Purpose purpose) throws RepeatitionException;

    List<Purpose> findPurposes();

}
