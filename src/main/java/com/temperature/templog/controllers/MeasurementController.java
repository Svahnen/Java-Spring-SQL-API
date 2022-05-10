package com.temperature.templog.controllers;

import com.temperature.templog.repositories.DAO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.temperature.templog.models.Measurement;

@RestController
public class MeasurementController {
    DAO dao = new DAO();

    @RequestMapping("/measurements/temperature")
    public List<Measurement> temperatures() {
        return dao.getAllMeasurements("temperature");
    }

    @RequestMapping("/measurements/moisture")
    public List<Measurement> moistures() {
        return dao.getAllMeasurements("moisture");
    }

    @RequestMapping("/measurements/{type}/{section}")
    public List<Measurement> temperaturesSection(@PathVariable String section, @PathVariable String type) {
        if (section.equals("a") && type.equals("temperature")) {
            return dao.getAllMeasurements("temperature", "a");
        } else if (section.equals("b") && type.equals("temperature")) {
            return dao.getAllMeasurements("temperature", "b");
        } else if (section.equals("c") && type.equals("temperature")) {
            return dao.getAllMeasurements("temperature", "c");
        } else if (section.equals("a") && type.equals("moisture")) {
            return dao.getAllMeasurements("moisture", "a");
        } else if (section.equals("b") && type.equals("moisture")) {
            return dao.getAllMeasurements("moisture", "b");
        } else if (section.equals("c") && type.equals("moisture")) {
            return dao.getAllMeasurements("moisture", "c");
        } else {
            return null;
        }
    }
}
