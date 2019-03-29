package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "purpose")
public class PurposeRestController {
    @Autowired
    private PurposeService purposeService;

    @RequestMapping(value = "/allPurposes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Purpose> show() {
        return purposeService.findPurposes();
    }


    @RequestMapping(value = "/newPurpose", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addPurpose(@RequestBody PurposeDto purposeDto) {
        Purpose purpose = new Purpose();
        purpose.setPurpose(purposeDto.getPurpose());
        try {
            purposeService.addPurpose(purpose);
            return "purpose";
        } catch (RepeatitionException e) {
            return "another page";
        }
    }

    @RequestMapping(value = "/{purposeId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Purpose findPurposeByName(@PathVariable("purposeId") int purposeId) throws PurposeNotFoundException {
        Purpose purpose = purposeService.findPurposeById(purposeId);
        return purpose;
    }

    @RequestMapping(value = "/purposeByName", method = RequestMethod.GET)
    public Purpose findPurposeByName(@RequestBody PurposeDto purposeDto) throws PurposeNotFoundException {
        Purpose purpose = purposeService.findPurposeByName(purposeDto.getPurpose());
        return purpose;
    }

    @RequestMapping(value = "/{purposeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubsection(@PathVariable("purposeId") int purposeId) {
        try {
            purposeService.deletePurpose(purposeId);
        } catch (CantDeleteElement cantDeleteElement) {

        }
    }
}
