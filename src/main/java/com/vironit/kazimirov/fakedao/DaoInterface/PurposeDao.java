package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface PurposeDao {

    void addPurpose(Purpose purpose) throws RepeatitionException;

    List<Purpose> findPurposes();

    Purpose findPurposeByName(String purposeName);

    void deletePurpose (int idPurpose);

}
