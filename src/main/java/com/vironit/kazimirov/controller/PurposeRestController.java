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
//@RequestMapping(value = "purpose")
public class PurposeRestController {
    @Autowired
    private PurposeService purposeService;

    @RequestMapping(value = "purpose/allPurposes", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<PurposeDto> show() {
        return purposeService.findPurposes();
    }


    @RequestMapping(value = "admin/purpose/newPurpose", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String addPurpose(@RequestBody PurposeDto purposeDto) {
//        Purpose purpose = new Purpose();
//        purpose.setPurpose(purposeDto.getPurpose());
        try {
            purposeService.addPurpose(purposeDto);
            return "purpose";
        } catch (RepeatitionException e) {
            return "another page";
        }
    }

    @RequestMapping(value = "purpose/find/{purposeId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    PurposeDto findPurposeByName(@PathVariable("purposeId") int purposeId) throws PurposeNotFoundException {
        return purposeService.findPurposeById(purposeId);
    }

    @RequestMapping(value = "purpose/purposeByName", method = RequestMethod.GET)
    public PurposeDto findPurposeByName(@RequestBody PurposeDto purposeDto) throws PurposeNotFoundException {
        return purposeService.findPurposeByName(purposeDto.getPurpose());
    }

    @RequestMapping(value = "admin/purpose/delete/{purposeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubsection(@PathVariable("purposeId") int purposeId) {
        try {
            purposeService.deletePurpose(purposeId);
        } catch (CantDeleteElement cantDeleteElement) {

        }
    }
}
