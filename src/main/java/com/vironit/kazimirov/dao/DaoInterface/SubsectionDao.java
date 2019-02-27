package com.vironit.kazimirov.dao.DaoInterface;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface SubsectionDao {
    void addSubsection(Subsection subsection) throws RepeatitionException;

    List<Subsection> findSubsections();
}