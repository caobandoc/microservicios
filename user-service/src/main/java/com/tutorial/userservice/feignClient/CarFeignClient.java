package com.tutorial.userservice.feignClient;

import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002")
public interface CarFeignClient {

    @PostMapping("/car")
    Car saveCar(@RequestBody Car car);

    @GetMapping("/car/byuser/{userId}")
    List<Car> getCarsByUserId(@PathVariable int userId);

}
