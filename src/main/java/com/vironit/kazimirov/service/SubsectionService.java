package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;

import java.util.List;

public interface SubsectionService {

    int addSubsection(SubsectionDto subsection) throws RepeatitionException;//выполнено

    List<SubsectionDto> findSubsections();//выполнено

    SubsectionDto findSubsectionByName(String subsectionTitle) throws SubsectionNotFoundException;//выполнено

    void deleteSubsection (int subsectionId) throws CantDeleteElement, SubsectionNotFoundException;//выполнено

    SubsectionDto findSubsectionById(int subsectionId) throws SubsectionNotFoundException;//выполнено
}
