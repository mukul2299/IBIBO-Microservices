package com.goibibo.UserService.external.services;

import com.goibibo.UserService.external.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "HOTELSERVICE")
public interface HotelService {

    @GetMapping("/hotels")
    List<Hotel> findHotels(@RequestParam(required = false) String hotelIds);
}
