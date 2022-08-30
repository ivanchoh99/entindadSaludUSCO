package com.entidadsaludusco.controller;

import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.service.CitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class AppControler {

    @Autowired
    CitasService citasService;

    @GetMapping("/fragments")
    public String fragments(){
        return "fragments";
    }

    @GetMapping(value = {"/","/login"})
    public String login() {
        return "login";
    }

    @GetMapping(value = "/forbidden")
    public String forbidden() {
        return "forbidden";
    }

    @GetMapping(value = "/error")
    public String error() {
        return "error";
    }

    @GetMapping("viewCitas")
    public ModelAndView citas(){
        ModelAndView mv = new ModelAndView();
        List<Cita> citas = citasService.findAll();
        mv.addObject("citas",citas);
        mv.setViewName("viewCitas");
        return mv;
    }


}
