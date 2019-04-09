package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.dto.SubsectionDto;
import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.CantDeleteElement;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.exception.SubsectionNotFoundException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SubsectionController {
    @Autowired
    private SubsectionService subsectionService;

    @RequestMapping(value = "/subsectionPage", method = RequestMethod.GET)
    public void redirectExample(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/subsection.jsp").forward(request, response);
    }

     @RequestMapping("/showSubsections")
    public String showSubsections(ModelMap map) {
         List<SubsectionDto> subsections=subsectionService.findSubsections();
         map.addAttribute("subsections",subsections);
         return "subsection";
     }

    @RequestMapping(value = "/addSubsection", method = RequestMethod.POST)
    public String addSubsection(@RequestParam ("title") String title, ModelMap map) throws RepeatitionException {
        SubsectionDto subsectionDto=new SubsectionDto();
        subsectionDto.setTitle(title);
        int subsectionId=subsectionService.addSubsection(subsectionDto);
        map.addAttribute("subsectionId",subsectionId);
        return "subsection";
    }

    @RequestMapping(value = "/findSubsectionByName")
    public String findPurposeByName(@RequestParam ("subsectionName") String purposeName, ModelMap map) throws SubsectionNotFoundException {
        SubsectionDto subsection=subsectionService.findSubsectionByName(purposeName);
        map.addAttribute("subsection", subsection);
        return "subsection";
    }

    @RequestMapping(value = "/findSubsectionById", method = RequestMethod.GET)
    public String findSubsectionById(@RequestParam ("id") int id, ModelMap map) throws SubsectionNotFoundException {
        SubsectionDto subsection=subsectionService.findSubsectionById(id);
        map.addAttribute("subsection", subsection);
        return "subsection";
    }

    @RequestMapping(value = "/findSubsectionLong")
    public String findSubsectionLong(ModelMap map) {
        List<SubsectionDto> subsections = subsectionService.findSubsections();
        String neverUseTitle=subsections.stream().map(SubsectionDto::getTitle).collect(Collectors.joining());
//        for(int i=0;i<subsections.size();i++){
//            neverUseTitle=neverUseTitle.concat(subsections.get(i).getTitle());
//        }
        SubsectionDto subsectionBeforeTest = subsections.get(subsections.size() - 1);
        subsectionBeforeTest.setTitle(neverUseTitle);
        map.addAttribute("subsectionBeforeTest",subsectionBeforeTest);
        return "subsection";
    }

    @RequestMapping(value = "/deleteSubsection")
    public ModelAndView deleteSubcetion(@RequestParam ("subsectionId") int subsectionId, ModelMap map){
        ModelAndView modelAndView=new ModelAndView();
        try{
         subsectionService.deleteSubsection(subsectionId);
         modelAndView.setViewName("subsection");
        } catch (CantDeleteElement cantDeleteElement) {
            modelAndView.setViewName("tryLogin");
        }
        return modelAndView;
    }
}
