package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.exception.GoodException;
import com.vironit.kazimirov.exception.GoodNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "good")
public class GoodRestController {
    @Autowired
    private GoodService goodService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGood(@RequestBody GoodDto goodDto) throws RepeatitionException, GoodException {
        Good good = goodDto.createGood();
        goodService.addGood(good);
    }

    @RequestMapping(value = "/allGoods", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Good> showAllGoods() {
        List<Good> goods = goodService.findAllGoods();
        return goods;
    }

    @RequestMapping(value = "/{goodId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGood(@PathVariable("goodId") int goodId) {
        goodService.deleteGood(goodId);
    }

    @RequestMapping(value = "/name/{goodName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Good findGoodByName(@PathVariable ("goodName")String goodName) throws GoodNotFoundException {
        Good good = goodService.findByNameGood(goodName);
        return good;
    }

    @RequestMapping(value = "/{goodId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Good findGoodById(@PathVariable("goodId") int goodId) throws GoodNotFoundException {
        Good good = goodService.findGoodById(goodId);
        return good;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGood(@RequestBody GoodDto goodDto) {
        Good good = goodDto.createGood();
        goodService.updateGood(goodDto.getId(),good);
    }

}
