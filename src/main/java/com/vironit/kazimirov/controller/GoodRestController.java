package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(value = "goodId")
public class GoodRestController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private SubsectionService subsectionService;
    @Autowired
    private PurposeService purposeService;

    @RequestMapping(value = "admin/good/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int addGood(@RequestBody GoodDto goodDto) throws RepeatitionException, GoodException, SubsectionNotFoundException, PurposeNotFoundException {
//        Subsection subsection=subsectionService.findSubsectionById(goodDto.getSubsectionId());
//        Purpose purpose=purposeService.findPurposeById(goodDto.getPurposeId());
//        Good goodId = goodDto.createGood();
//        goodId.setSubsection(subsection);
//        goodId.setPurpose(purpose);
        int goodId = goodService.addGood(goodDto);
        return goodId;
    }

    @RequestMapping(value = "good/name", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GoodDto findGoodByName(@RequestBody String goodName) throws GoodNotFoundException {
        GoodDto goodDto = goodService.findByNameGood(goodName);
        return goodDto;
    }

    @RequestMapping(value = "good/allGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> showAllGoods() {
        List<GoodDto> goods = goodService.findAllGoods();
        return goods;
    }

    @RequestMapping(value = "good/goodsBySubsection", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> findGoodsBySubsection(@RequestBody SubsectionDto subsectionDto) throws SubsectionNotFoundException {

        List<GoodDto> goodDtos = goodService.findBySubsection(subsectionDto);
        return goodDtos;
    }

    @RequestMapping(value = "good/goodsByPurpose", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> findGoodsByPurpose(@RequestBody PurposeDto purposeDto) throws PurposeNotFoundException {
        List<GoodDto> goodDtos = goodService.findByPurpose(purposeDto);
        return goodDtos;
    }

    @RequestMapping(value = "admin/good/delete/{goodId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteGood(@PathVariable("goodId") int goodId) {
        goodService.deleteGood(goodId);
    }

    @RequestMapping(value = "admin/good/newPrice", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changePrice(@RequestBody GoodDto goodDto) throws GoodException {
        goodService.changePrice(goodDto.getId(), goodDto.getPrice());
    }

    @RequestMapping(value = "admin/good/newSubsection", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void changeSubsection(@RequestBody GoodDto goodDto) throws SubsectionNotFoundException {
        SubsectionDto subsectionDto = subsectionService.findSubsectionById(goodDto.getSubsectionId());
        goodService.changeSubsection(goodDto.getId(), subsectionDto);
    }

    @RequestMapping(value = "admin/good/newPurpose", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePurpose(@RequestBody GoodDto goodDto) throws PurposeNotFoundException {
        PurposeDto purposeDto = purposeService.findPurposeById(goodDto.getPurposeId());
        goodService.changePurpose(goodDto.getId(), purposeDto);
    }

    @RequestMapping(value = "admin/good/newUnit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUnit(@RequestBody GoodDto goodDto) {
        goodService.changeUnit(goodDto.getId(), goodDto.getUnit());
    }

    @RequestMapping(value = "admin/good/newQuantity", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeQuantity(@RequestBody GoodDto goodDto) {
        goodService.changeQuantity(goodDto.getId(), goodDto.getQuantity());
    }

    @RequestMapping(value = "admin/good/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@RequestBody GoodDto goodDto) throws GoodException {
        goodService.changeAmount(goodDto.getId(), goodDto.getAmount());
    }

    @RequestMapping(value = "admin/good/updateGood", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGood(@RequestBody GoodDto goodDto) throws GoodNotFoundException, GoodException {
        //Good goodId = goodDto.createGood();
        goodService.updateGood(goodDto.getId(), goodDto);
    }

    @RequestMapping(value = "good/goodsByPrice", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodDto> findGoodsByPrice(@RequestParam("minPrice") double minPrice,
                                       @RequestParam("maxPrice") double maxPrice) {
        List<GoodDto> goods = goodService.findGoodsByPrice(minPrice, maxPrice);
        return goods;
    }

    @RequestMapping(value = "good/findGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GoodDto findGoodById(@PathVariable("goodId") int goodId) throws GoodNotFoundException {
        GoodDto goodDto = goodService.findGoodById(goodId);
        return goodDto;
    }

}
