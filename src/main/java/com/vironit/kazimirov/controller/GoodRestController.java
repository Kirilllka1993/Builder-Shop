package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.GoodDto;
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
@RequestMapping(value = "good")
public class GoodRestController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private SubsectionService subsectionService;
    @Autowired
    private PurposeService purposeService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int addGood(@RequestBody GoodDto goodDto) throws RepeatitionException, GoodException, SubsectionNotFoundException, PurposeNotFoundException {
        Subsection subsection=subsectionService.findSubsectionById(goodDto.getSubsectionId());
        Purpose purpose=purposeService.findPurposeById(goodDto.getPurposeId());
        Good good = goodDto.createGood();
        good.setSubsection(subsection);
        good.setPurpose(purpose);
        int goodId=goodService.addGood(good);
        return goodId;
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Good findGoodByName(@RequestBody String goodName) throws GoodNotFoundException {
        Good good = goodService.findByNameGood(goodName);
        return good;
    }

    @RequestMapping(value = "/allGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> showAllGoods() {
        List<Good> goods = goodService.findAllGoods();
        return goods;
    }

    @RequestMapping(value = "/goodsBySubsection", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> findGoodsBySubsection(@RequestBody String title) throws SubsectionNotFoundException {
        Subsection subsection = subsectionService.findSubsectionByName(title);
        List<Good> goods = goodService.findBySubsection(subsection);
        return goods;
    }

    @RequestMapping(value = "/goodsByPurpose", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> findGoodsByPurpose(@RequestBody String purposeName) throws PurposeNotFoundException {
        Purpose purpose = purposeService.findPurposeByName(purposeName);
        List<Good> goods = goodService.findByPurpose(purpose);
        return goods;
    }

    @RequestMapping(value = "/delete/{goodId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGood(@PathVariable("goodId") int goodId) {
        goodService.deleteGood(goodId);
    }

    @RequestMapping(value = "/newPrice", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePrice(@RequestBody GoodDto goodDto) throws GoodException {
        goodService.changePrice(goodDto.getId(), goodDto.getPrice());
    }

    @RequestMapping(value = "/newSubsection", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeSubsection(@RequestBody GoodDto goodDto)  throws SubsectionNotFoundException {
        Subsection subsection = subsectionService.findSubsectionByName(goodDto.getName());
        goodService.changeSubsection(goodDto.getId(), subsection);
    }

    @RequestMapping(value = "/newPurpose", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePurpose(@RequestBody GoodDto goodDto) throws PurposeNotFoundException {
        Purpose purpose = purposeService.findPurposeByName(goodDto.getName());
        goodService.changePurpose(goodDto.getId(), purpose);
    }

    @RequestMapping(value = "/newUnit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeUnit(@RequestBody GoodDto goodDto) {
        goodService.changeUnit(goodDto.getId(), goodDto.getUnit());
    }

    @RequestMapping(value = "/newQuantity", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeQuantity(@RequestBody GoodDto goodDto) {
        goodService.changeQuantity(goodDto.getId(), goodDto.getQuantity());
    }

    @RequestMapping(value = "/newAmount", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAmount(@RequestBody GoodDto goodDto) throws GoodException {
        goodService.changeAmount(goodDto.getId(), goodDto.getAmount());
    }

    @RequestMapping(value = "/updateGood",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGood(@RequestBody GoodDto goodDto) {
        Good good = goodDto.createGood();
        goodService.updateGood(goodDto.getId(), good);
    }

    @RequestMapping(value = "/goodsByPrice", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> findGoodsByPrice(@RequestParam("minPrice") double minPrice,
                                       @RequestParam("maxPrice") double maxPrice) {
        List<Good> goods = goodService.findGoodsByPrice(minPrice, maxPrice);
        return goods;
    }

    @RequestMapping(value = "/findGood/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Good findGoodById(@PathVariable("goodId") int goodId) throws GoodNotFoundException {
        Good good = goodService.findGoodById(goodId);
        return good;
    }

}
