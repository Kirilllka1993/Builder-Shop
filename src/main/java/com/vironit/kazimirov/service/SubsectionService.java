package com.vironit.kazimirov.service;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;

import java.util.List;

public interface SubsectionService {

    void addSubsection(Subsection subsection) throws RepeatitionException;

    List<Subsection> findSubsections();
}
