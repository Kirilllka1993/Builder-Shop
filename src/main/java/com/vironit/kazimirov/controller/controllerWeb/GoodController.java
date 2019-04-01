package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.dto.GoodDto;
import com.vironit.kazimirov.entity.Good;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.*;
import com.vironit.kazimirov.service.GoodService;
import com.vironit.kazimirov.service.PurposeService;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class GoodController extends HttpServlet {
    @Autowired
    private GoodService goodService;
    @Autowired
    private PurposeService purposeService;
    @Autowired
    private SubsectionService subsectionService;

    @RequestMapping(value = "/goodPage", method = RequestMethod.GET)
    public ModelAndView moveToGoodPage(ModelMap map) {
        List<Purpose> purposes = purposeService.findPurposes();
        map.addAttribute("purposes", purposes);
        List<Subsection> subsections = subsectionService.findSubsections();
        map.addAttribute("subsections", subsections);
        ModelAndView modelAndView = new ModelAndView();
        map.addAttribute("command", new GoodDto());
        modelAndView.addAllObjects(map);
        modelAndView.setViewName("good");
        return modelAndView;
    }

    @RequestMapping(value = "/addGood", method = RequestMethod.POST)
    public ModelAndView addGood(@ModelAttribute GoodDto goodDto, BindingResult result, ModelMap map) throws GoodException, RepeatitionException, SubsectionNotFoundException, PurposeNotFoundException {
        Good good = new Good();
//        Subsection subsection = subsectionService.findSubsectionByName(goodDto.getSubsection().getTitle());
//        Purpose purpose = purposeService.findSubsectionById(goodDto.getPurpose().getPurpose());
        Subsection subsection=subsectionService.findSubsectionById(goodDto.getSubsectionId());
        Purpose purpose=purposeService.findPurposeById(goodDto.getPurposeId());
        map.addAttribute("command", goodDto);
        good.setName(goodDto.getName());
        good.setUnit(goodDto.getUnit());
        good.setQuantity(goodDto.getQuantity());
        good.setDiscount(goodDto.getDiscount());
        good.setPrice(goodDto.getPrice());
        good.setAmount(goodDto.getAmount());
        good.setPurpose(purpose);
        good.setSubsection(subsection);
        goodService.addGood(good);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("good");

//    public String addGood(@RequestParam("price") double price,
//                          @RequestParam("subsection") String title,
//                          @RequestParam("unit") String unit,
//                          @RequestParam("quantity") int quantity,
//                          @RequestParam("discount") double discount,
//                          @RequestParam("purpose") String purposeName,
//                          @RequestParam("name") String name,
//                          @RequestParam("amount") int amount) throws GoodException {
//
//        Subsection subsection=subsectionService.findSubsectionByName(title);
//        Purpose purpose=purposeService.findSubsectionById(purposeName);
//        Good good = new Good();
//        good.setSubsection(subsection);
//        good.setPurpose(purpose);
//        good.setPrice(price);
//        good.setName(name);
//        good.setDiscount(discount);
//        good.setQuantity(quantity);
//        good.setAmount(amount);
//        good.setUnit(unit);
//        goodService.addGood(good);
        return modelAndView;
    }

    @RequestMapping(value = "/showGoods", method = RequestMethod.GET)
    public ModelAndView showGoods(ModelMap map) throws SQLException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("good");
        List<Good> goods = goodService.findAllGoods();
        map.addAttribute("goods", goods);
        map.addAttribute("command", new GoodDto());
        List<Purpose> purposes = purposeService.findPurposes();
        map.addAttribute("purposes", purposes);
        return modelAndView;
    }

    @RequestMapping(value = "/findGoodByName", method = RequestMethod.GET)
    public ModelAndView findGoodByName(@RequestParam("goodName") String goodName, ModelMap map) throws GoodNotFoundException {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("good");
        map.addAttribute("command",new GoodDto());
        Good good = goodService.findByNameGood(goodName);
        map.addAttribute("good",good);
        return modelAndView;
    }

    @RequestMapping(value = "/findBySubsection", method = RequestMethod.GET)
    public ModelAndView findGoodsBySubsection(@RequestParam("subsection") String title, ModelMap map) throws SubsectionNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("good");
        Subsection subsection=subsectionService.findSubsectionByName(title);
        List<Good> goods=goodService.findBySubsection(subsection);
        map.addAttribute("goods", goods);
        map.addAttribute("command", new GoodDto());
        return modelAndView;
    }

    @RequestMapping(value = "/findGoodByPrice", method = RequestMethod.GET)
    public ModelAndView findGoodsByPrice(@RequestParam("minPrice") double minPrice,
                                         @RequestParam("maxPrice") double maxPrice, ModelMap map){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("good");
        List<Good> goods=goodService.findGoodsByPrice(minPrice,maxPrice);
        map.addAttribute("goods1", goods);
        map.addAttribute("command", new GoodDto());
        return modelAndView;
    }
}
