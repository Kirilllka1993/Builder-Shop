package com.vironit.kazimirov.fakedao.DaoInterface;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface SubsectionDao {
    int addSubsection(Subsection subsection) throws RepeatitionException;

    List<Subsection> findSubsections();

    Subsection findSubsectionByName(String subsectionTitle);

    void deleteSubsection(int subsectionId) throws CantDeleteElement;

    Subsection findSubsectionById(int subsectionId);
}
