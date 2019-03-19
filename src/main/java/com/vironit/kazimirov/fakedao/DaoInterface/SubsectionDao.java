package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface SubsectionDao {
    void addSubsection(Subsection subsection) throws RepeatitionException;

    List<Subsection> findSubsections();

    Subsection findSubsectionByName(String title);
}
