package com.example.TestTaskOvsiyAurosKS.controller;

import com.example.TestTaskOvsiyAurosKS.entity.DHTMLXAdapter;
import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.service.KPacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/kpacs")
public class KPacController {

    @Autowired
    private KPacService kPacService;

    @GetMapping
    public ModelAndView kpacs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("kpacs.html");
        modelAndView.addObject("name", "Vasya");

        return modelAndView;
    }

    @RequestMapping(value = "/kpacsdata", method = RequestMethod.GET)
    @ResponseBody
    public DHTMLXAdapter<KPac> getAllKPacs() {
        return kPacService.getAllKPacs();
    }

    @PostMapping("/addNewKpac")
    public RedirectView addKPac(KPac kPac) {
        kPac.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        kPacService.addKPac(kPac);

        return new RedirectView("/kpacs");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteKPac(@PathVariable long id) {
        kPacService.deleteKPac(kPacService.findKPacById(id));
    }
}
