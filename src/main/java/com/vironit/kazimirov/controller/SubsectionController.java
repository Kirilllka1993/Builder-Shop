package com.vironit.kazimirov.controller;

import com.vironit.kazimirov.entity.Subsection;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.SubsectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
         List<Subsection> subsections=subsectionService.findSubsections();
         map.addAttribute("subsections",subsections);
         return "subsection";
     }

    @RequestMapping(value = "/addSubsection", method = RequestMethod.POST)
    public String addSubsection(@RequestParam ("title") String title, ModelMap map) throws RepeatitionException {
        Subsection subsection=new Subsection();
        subsection.setTitle(title);
        subsectionService.addSubsection(subsection);
        return "subsection";
    }

    @RequestMapping(value = "/findSubsectionByName")
    public String findPurposeByName(@RequestParam ("subsectionName") String purposeName, ModelMap map){
        Subsection subsection=null;
        subsection=subsectionService.findSubsectionByName(purposeName);
        map.addAttribute("subsection", subsection);
        return "subsection";
    }
}
