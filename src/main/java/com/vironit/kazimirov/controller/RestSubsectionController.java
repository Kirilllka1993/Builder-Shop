package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping
//@RequestMapping(value = "/subsection/rest")
public class RestSubsectionController {
    @Autowired
    private SubsectionService subsectionService;

//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public void create(@RequestBody Subsection subsection) throws RepeatitionException {
//        subsectionService.addSubsection(subsection);
//    }

    @RequestMapping(value = "/showSubsections", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Subsection> show() {//@PathVariable(value = "subsectionId"int subsectionId)
        return subsectionService.findSubsections();
    }


    //    @RequestMapping(value = "/addSubsection", method = RequestMethod.POST)
//    public String addSubsection(@RequestParam ("title") String title, ModelMap map) throws RepeatitionException {
//        Subsection subsection=new Subsection();
//        subsection.setTitle(title);
//        subsectionService.addSubsection(subsection);
//        return "subsection";
//    }

    @RequestMapping(value = "/findSubsectionByName", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Subsection findPurposeByName(@RequestBody String subsectionName){
        Subsection subsection= subsectionService.findSubsectionByName(subsectionName);
        return subsection;
    }
//
//    @RequestMapping(value = "/findSubsectionLong")
//    public String findSubsectionLong(ModelMap map) {
//        List<Subsection> subsections = subsectionService.findSubsections();
//        String neverUseTitle=subsections.stream().map(Subsection::getTitle).collect(Collectors.joining());
////        for(int i=0;i<subsections.size();i++){
////            neverUseTitle=neverUseTitle.concat(subsections.get(i).getTitle());
////        }
//        Subsection subsectionBeforeTest = subsections.get(subsections.size() - 1);
//        subsectionBeforeTest.setTitle(neverUseTitle);
//        map.addAttribute("subsectionBeforeTest",subsectionBeforeTest);
//        return "subsection";
//    }
//
//    @RequestMapping(value = "/deleteSubsection")
//    public ModelAndView deleteSubcetion(@RequestParam ("subsectionId") int subsectionId, ModelMap map){
//        ModelAndView modelAndView=new ModelAndView();
//        try{
//            subsectionService.deleteSubsection(subsectionId);
//            modelAndView.setViewName("subsection");
//        } catch (CantDeleteElement cantDeleteElement) {
//            modelAndView.setViewName("tryLogin");
//        }
//        return modelAndView;
//    }
}
