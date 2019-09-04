package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("shop")
public class SubsectionRestController {
    @Autowired
    private SubsectionService subsectionService;

    @CrossOrigin
    @RequestMapping(value = "subsection/allSubsections", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<SubsectionDto> show() {
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        return subsections;
    }


    @RequestMapping(value = "admin/subsection/newSubsection", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public int addSubsection(@RequestBody SubsectionDto subsectionDto) throws RepeatitionException {
        return subsectionService.addSubsection(subsectionDto);
    }

    @RequestMapping(value = "subsection/findById/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    SubsectionDto findSubsectionById(@PathVariable("id") int id) throws SubsectionNotFoundException {
        SubsectionDto subsection = subsectionService.findSubsectionById(id);
        return subsection;
    }

    @RequestMapping(value = "subsection/subsectionByName", method = RequestMethod.POST)
    public SubsectionDto findSubsectionByName(@RequestBody SubsectionDto subsectionDto) throws SubsectionNotFoundException {
        SubsectionDto subsection = subsectionService.findSubsectionByName(subsectionDto.getTitle());
        return subsection;
    }

    @RequestMapping(value = "admin/subsection/delete/{subsectionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteSubsection(@PathVariable("subsectionId") int subsectionId) throws SubsectionNotFoundException, CantDeleteElement {
            subsectionService.deleteSubsection(subsectionId);
    }
}
