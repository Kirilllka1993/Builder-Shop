package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "subsection")
public class SubsectionRestController {
    @Autowired
    private SubsectionService subsectionService;

    @RequestMapping(value = "/allSubsections", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Subsection> show() {//@PathVariable(value = "subsectionId"int subsectionId)
        return subsectionService.findSubsections();
    }


    @RequestMapping(value = "/newSubsection", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addSubsection(@RequestBody SubsectionDto subsectionDto) throws RepeatitionException {
        Subsection subsection = new Subsection();
        subsection.setTitle(subsectionDto.getTitle());
        subsectionService.addSubsection(subsection);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Subsection findPurposeByName(@PathVariable("id") int id) {
        Subsection subsection = subsectionService.findSubsectionById(id);
        return subsection;
    }

    @RequestMapping(value = "/subsectionByName", method = RequestMethod.GET)
    public Subsection findSubsectionByName(@RequestBody SubsectionDto subsectionDto) {
        Subsection subsection = subsectionService.findSubsectionByName(subsectionDto.getTitle());
        return subsection;
    }

    @RequestMapping(value = "/{subsectionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubsection(@PathVariable("subsectionId") int subsectionId) {
        try {
            subsectionService.deleteSubsection(subsectionId);
        } catch (CantDeleteElement cantDeleteElement) {

        }
    }
}
