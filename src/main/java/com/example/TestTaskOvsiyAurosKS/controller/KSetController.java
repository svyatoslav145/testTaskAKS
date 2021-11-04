package com.example.TestTaskOvsiyAurosKS.controller;

import com.example.TestTaskOvsiyAurosKS.entity.DHTMLXAdapter;
import com.example.TestTaskOvsiyAurosKS.entity.KPac;
import com.example.TestTaskOvsiyAurosKS.entity.KSet;
import com.example.TestTaskOvsiyAurosKS.service.KSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/ksets")
public class KSetController {

    @Autowired
    private KSetService kSetService;

    @GetMapping
    public ModelAndView ksets() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("ksets.html");
        modelAndView.addObject("name", "Vasya");

        return modelAndView;
    }

    @RequestMapping(value = "/ksetsdata", method = RequestMethod.GET)
    @ResponseBody
    public DHTMLXAdapter<KSet> getAllKSets() {
        return kSetService.getAllSet();
    }

    @PostMapping("/addNewKSet")
    public RedirectView addKSet(KSet kSet, String kPacs) {
        kSetService.addKSet(kSet, kPacs);

        return new RedirectView("/ksets");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteKSet(@PathVariable long id) {
        kSetService.deleteKSet(kSetService.findKSetById(id));
    }

    @GetMapping("/set/{id}")
    public ModelAndView kPacsFromKSet(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("kpacs_from_kset.html");
        modelAndView.addObject("id", id);
        modelAndView.addObject("name", kSetService.findKSetById(id).getTitle());

        return modelAndView;
    }

    @GetMapping("/ksetsdata/{id}")
    @ResponseBody
    public DHTMLXAdapter<KPac> getKPacsFromKSet(@PathVariable long id) {
        return kSetService.getKPacsFromKSet(kSetService.findKSetById(id));
    }
}
