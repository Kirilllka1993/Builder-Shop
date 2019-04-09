package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;
import com.vironit.kazimirov.fakedao.DaoInterface.GoodDao;
import com.vironit.kazimirov.fakedao.DaoInterface.SubsectionDao;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubsectionServiceImpl implements SubsectionService {

    @Autowired
    private final SubsectionDao subsectionDao;
    @Autowired
    private final GoodDao goodDao;

    @Autowired
    public SubsectionServiceImpl(SubsectionDao subsectionDao, GoodDao goodDao) {
        this.subsectionDao = subsectionDao;
        this.goodDao = goodDao;
    }

    @Override
    public int addSubsection(SubsectionDto subsectionDto) throws RepeatitionException {
        Subsection subsection = new Subsection();
        subsection.setTitle(subsectionDto.getTitle());
        Optional<Subsection> subsection1 = Optional.ofNullable(subsectionDao.findSubsectionByName(subsection.getTitle()));
        if (subsection1.isPresent() == false) {
            return subsectionDao.addSubsection(subsection);
        } else {
            throw new RepeatitionException("such subsection is present");
        }
    }

    @Override
    public List<SubsectionDto> findSubsections() {
        List<Subsection> subsections = subsectionDao.findSubsections();
        List<SubsectionDto> subsectionDtos = new ArrayList<>();
        for (int i = 0; i < subsections.size(); i++) {
            SubsectionDto subsectionDto = new SubsectionDto();
            subsectionDto.setId(subsections.get(i).getId());
            subsectionDto.setTitle(subsections.get(i).getTitle());
            subsectionDtos.add(subsectionDto);
        }
        return subsectionDtos;
    }

    @Override
    public SubsectionDto findSubsectionByName(String subsectionTitle) throws SubsectionNotFoundException {
        Optional<Subsection> checkNameSubsection = Optional.ofNullable(subsectionDao.findSubsectionByName(subsectionTitle));
        if (checkNameSubsection.isPresent() == false) {
            throw new SubsectionNotFoundException("such subsection is absent");
        } else {
            Subsection subsection=subsectionDao.findSubsectionByName(subsectionTitle);
            SubsectionDto subsectionDto=new SubsectionDto();
            subsectionDto.setId(subsection.getId());
            subsectionDto.setTitle(subsection.getTitle());
            return subsectionDto;
        }
    }

    @Override
    public void deleteSubsection(int subsectionId) throws CantDeleteElement {
        Subsection subsection = subsectionDao.findSubsectionById(subsectionId);
        List<Good> goods = goodDao.findBySubsection(subsection);
        if (goods.size() == 0) {
            subsectionDao.deleteSubsection(subsectionId);
        } else {
            throw new CantDeleteElement("this subsection is using with goods");
        }
    }

    @Override
    public SubsectionDto findSubsectionById(int subsectionId) throws SubsectionNotFoundException {
        Optional<Subsection> checkIdSubsection = Optional.ofNullable(subsectionDao.findSubsectionById(subsectionId));
        if (checkIdSubsection.isPresent() == false) {
            throw new SubsectionNotFoundException("such subsection is absent");
        } else {
            Subsection subsection=subsectionDao.findSubsectionById(subsectionId);
            SubsectionDto subsectionDto=new SubsectionDto();
            subsectionDto.setId(subsection.getId());
            subsectionDto.setTitle(subsection.getTitle());
            return subsectionDto;
        }

    }
}


