package com.temperature.templog.controllers;

import com.temperature.templog.repositories.DAO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThymeleafController {
    DAO dao = DAO.getInstance();

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("temperature", dao.getLatestMeasurement("temperature"));
        model.addAttribute("moisture", dao.getLatestMeasurement("moisture"));
        model.addAttribute("price", dao.getLatestPrice());
        return "index";
    }
}