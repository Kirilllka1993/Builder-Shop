package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;

import java.util.List;

public interface SubsectionService {

    int addSubsection(SubsectionDto subsection) throws RepeatitionException;

    List<SubsectionDto> findSubsections();

    SubsectionDto findSubsectionByName(String subsectionTitle) throws SubsectionNotFoundException;

    void deleteSubsection (int subsectionId) throws CantDeleteElement, SubsectionNotFoundException;

    SubsectionDto findSubsectionById(int subsectionId) throws SubsectionNotFoundException;
}
