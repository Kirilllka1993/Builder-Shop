package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;

import java.util.List;

public interface SubsectionService {

    int addSubsection(Subsection subsection) throws RepeatitionException;

    List<Subsection> findSubsections();

    Subsection findSubsectionByName(String subsectionTitle) throws SubsectionNotFoundException;

    void deleteSubsection (int subsectionId)throws CantDeleteElement;

    Subsection findSubsectionById(int subsectionId) throws SubsectionNotFoundException;
}
