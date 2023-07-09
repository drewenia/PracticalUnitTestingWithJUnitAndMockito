package ch10;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CarSearch {
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car){
        cars.add(car);
    }

    public List<Car> findSportCars(){
        List<Car> sportCars = new ArrayList<>();
        cars.forEach(car->{
            if (Color.RED == car.getColor()
                    && "Ferrari".equals(car.getManufacturer().getName())
                    && car.getEngine().getNumberOfCylinders() > 6
            )
            {
                sportCars.add(car);
            }
        });
        return sportCars;
    }
}
