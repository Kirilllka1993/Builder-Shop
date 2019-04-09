package com.vironit.kazimirov.service;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;

import java.util.List;

public interface SubsectionService {

    int addSubsection(SubsectionDto subsection) throws RepeatitionException;//админ

    List<SubsectionDto> findSubsections();//пользователь

    SubsectionDto findSubsectionByName(String subsectionTitle) throws SubsectionNotFoundException;//пользователь

    void deleteSubsection (int subsectionId)throws CantDeleteElement;//админ

    SubsectionDto findSubsectionById(int subsectionId) throws SubsectionNotFoundException;//пользователь
}
