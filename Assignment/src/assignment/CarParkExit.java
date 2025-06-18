/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author dougl
 */
public class CarParkExit extends Barrier {
    double earn;
    int totalParkingDuration;
    
    public CarParkExit(String barrierName, int maxQueue, CenterSystem centerSystem) {
        super(barrierName, maxQueue, centerSystem);
        earn = 0;
        totalParkingDuration = 0;
    }
    
    @Override
    void simulate(Car car)
    {
        
        try {
            double price = centerSystem.calculatePrice(car.entryHour, car.entryMinute);
            int parkTime = centerSystem.getParkingDuration(car);
            totalParkingDuration+=parkTime;
            System.out.println(barrierName + ": Car" + car.id + " park for " + parkTime + " minutes" + (centerSystem.open ? "." : " and get fine due to overtime."));
            System.out.println(barrierName + ": Car" + car.id + " is paying RM" + price + " for exit.");
            Thread.sleep((int)(Math.random() * (5000 - 1000 + 1)) + 1000);
            System.out.println(barrierName + ": Car" + car.id + " is exit the car park.");
            earn+=price;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
