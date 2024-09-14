package com.goibibo.hotel.service;

import com.goibibo.hotel.entities.Hotel;

import java.util.List;
import java.util.Set;

public interface HotelService {
    List<Hotel> getAllHotels();

    List<Hotel> getAllHotelsByIds(Set<String> hotelIds);

    Hotel getHotelById(String id);
    Hotel createHotel(Hotel hotel);
}
