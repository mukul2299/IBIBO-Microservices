package com.goibibo.hotel.controllers;

import com.goibibo.hotel.entities.Hotel;
import com.goibibo.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private static final String TOPIC = "hotel-topic";
    @Autowired
    HotelService hotelService;
    @Autowired
    KafkaTemplate<String, List<Hotel>> kafkaTemplate;

    @PostMapping
    ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
    }

    @GetMapping
    ResponseEntity<String> getHotels(@RequestParam(required = false) Set<String> ids) {
        List<Hotel> response;
        if (CollectionUtils.isEmpty(ids)) {
            response = hotelService.getAllHotels();
        } else {
            response = hotelService.getAllHotelsByIds(ids);
        }
        kafkaTemplate.send(TOPIC, response);
        return ResponseEntity.status(HttpStatus.OK).body("Hotel List Sent to Kafka");
    }

    @GetMapping("/{hotelId}")
    ResponseEntity<Hotel> getHotel(@PathVariable String hotelId) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(hotelId));
    }
}
