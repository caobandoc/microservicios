package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.Userr;
import com.tutorial.userservice.feignclient.BikeFeignClient;
import com.tutorial.userservice.feignclient.CarFeignClient;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    RestTemplate restTemplate;
    CarFeignClient carFeignClient;
    BikeFeignClient bikeFeignClient;

    public List<Userr> getAllUsers() {
        return userRepository.findAll();
    }

    public Userr getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Userr saveUser(Userr userr) {
        return userRepository.save(userr);
    }

    public List<Car> getCars(int id){
        return restTemplate.getForObject("http://car-service/car/byuser/" + id, List.class);
    }

    public List<Bike> getBikes(int id){
        return restTemplate.getForObject("http://bike-service/bike/byuser/" + id, List.class);
    }

    public Car saveCar(int userId, Car car) {
        car.setUserId(userId);
        return carFeignClient.saveCar(car);
    }

    public Bike saveBike(int userId, Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.saveBike(bike);
    }

    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        Userr user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            result.put("Mensaje", "El usuario no existe");
            return result;
        }
        result.put("User", user);

        List<Car> cars = carFeignClient.getCarsByUserId(userId);
        if (cars.isEmpty()) {
            result.put("Cars", "El usuario no tiene carros");
        } else {
            result.put("Cars", cars);
        }

        List<Bike> bikes = bikeFeignClient.getBikesByUserId(userId);
        if (bikes.isEmpty()) {
            result.put("Bikes", "El usuario no tiene motos");
        } else {
            result.put("Bikes", bikes);
        }

        return result;
    }
}
