package com.goibibo.hotel.service.impl;

import com.goibibo.hotel.entities.Hotel;
import com.goibibo.hotel.exceptions.ResourceNotFoundException;
import com.goibibo.hotel.repositories.HotelRepository;
import com.goibibo.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public List<Hotel> getAllHotelsByIds(Set<String> hotelIds) {
        return hotelRepository.findAllById(hotelIds);
    }

    @Override
    public Hotel getHotelById(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with given id: " + id));
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        hotel.setId(UUID.randomUUID().toString());
        return hotelRepository.save(hotel);
    }
}
