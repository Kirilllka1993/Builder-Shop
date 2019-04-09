package com.vironit.kazimirov.controller.controllerWeb;

import com.vironit.kazimirov.dto.PurposeDto;
import com.vironit.kazimirov.entity.Purpose;
import com.vironit.kazimirov.exception.PurposeNotFoundException;
import com.vironit.kazimirov.exception.RepeatitionException;
import com.vironit.kazimirov.service.PurposeService;
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
public class PurposeController {
    @Autowired
    private PurposeService purposeService;

    @RequestMapping(value = "/purposePage", method = RequestMethod.GET)
    public void redirectExample(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/purpose.jsp").forward(request, response);
    }

    @RequestMapping("/showPurposes")
    public String showSubsections(ModelMap map) {
        List<PurposeDto> purposes=purposeService.findPurposes();
        map.addAttribute("purposes",purposes);
        return "purpose";
    }

    @RequestMapping(value = "/addPurpose", method = RequestMethod.POST)
    public String addSubsection(@RequestParam("purpose") String purpose1) throws RepeatitionException {
        PurposeDto purposeDto=new PurposeDto();
        purposeDto.setPurpose(purpose1);
        purposeService.addPurpose(purposeDto);
        return "purpose";
    }

    @RequestMapping(value = "/findPurposeByName")
    public String findPurposeByName(@RequestParam ("purposeName") String purposeName, ModelMap map) throws PurposeNotFoundException {
        PurposeDto purposeDto=purposeService.findPurposeByName(purposeName);
        map.addAttribute("purpose", purposeDto);
        return "purpose";
    }

    @RequestMapping(value = "/doPurpose1")
    public String doPurpose(ModelMap map) {
        List<PurposeDto> purposes = purposeService.findPurposes();
        map.addAttribute("purposes", purposes);
        return "purpose";
    }
}
