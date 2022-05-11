package com.temperature.templog.controllers;

import com.temperature.templog.repositories.DAO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.temperature.templog.models.Measurement;

@RestController
public class MeasurementController {
    DAO dao = new DAO();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/measurements")
    public List<Measurement> measurements() {
        return dao.getAllMeasurements();
    }

    @RequestMapping("/measurements/temperature")
    public List<Measurement> temperatures() {
        return dao.getAllMeasurements("temperature");
    }

    @RequestMapping("/measurements/moisture")
    public List<Measurement> moistures() {
        return dao.getAllMeasurements("moisture");
    }

    @RequestMapping("/measurement/{id}/delete")
    public boolean deleteMeasurement(@PathVariable int id) {
        return dao.deleteMeasurement(id);
    }

    @RequestMapping("/measurement/{id}")
    public Measurement getMeasurement(@PathVariable int id) {
        return dao.getMeasurement(id);
    }

    @RequestMapping("/measurement/latest/{type}")
    public Measurement getLatestMeasurement(@PathVariable String type) {
        return dao.getLatestMeasurement(type);
    }

    @RequestMapping("/measurements/{type}/{section}")
    public List<Measurement> temperaturesSection(@PathVariable String section, @PathVariable String type) {
        // Hard coded if statements so no sql injection is possible
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

    @PostMapping("/measurement/add")
    public void addMeasurement(@RequestBody Measurement measurement) {
        dao.addMeasurement(new Measurement(measurement.getValue(), LocalDateTime.now(), measurement.getType(),
                measurement.getSection()));
    }
}
