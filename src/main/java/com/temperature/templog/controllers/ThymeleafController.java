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
        model.addAttribute("ALatestTemperature", dao.getLatestMeasurement("temperature", "a"));
        model.addAttribute("BLatestTemperature", dao.getLatestMeasurement("temperature", "b"));
        model.addAttribute("CLatestTemperature", dao.getLatestMeasurement("temperature", "c"));
        model.addAttribute("ALatestMoisture", dao.getLatestMeasurement("moisture", "a"));
        model.addAttribute("BLatestMoisture", dao.getLatestMeasurement("moisture", "b"));
        model.addAttribute("CLatestMoisture", dao.getLatestMeasurement("moisture", "c"));
        model.addAttribute("AAverageTemperature", dao.getAverageMeasurement("temperature", "a"));
        model.addAttribute("BAverageTemperature", dao.getAverageMeasurement("temperature", "b"));
        model.addAttribute("CAverageTemperature", dao.getAverageMeasurement("temperature", "c"));
        model.addAttribute("AAverageMoisture", dao.getAverageMeasurement("moisture", "a"));
        model.addAttribute("BAverageMoisture", dao.getAverageMeasurement("moisture", "b"));
        model.addAttribute("CAverageMoisture", dao.getAverageMeasurement("moisture", "c"));
        return "index";
    }
}