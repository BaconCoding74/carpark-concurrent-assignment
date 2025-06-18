/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;


/**
 *
 * @author dougl
 */
public class CarParkEntry extends Barrier {
    int carEntered;

    public CarParkEntry(String barrierName, int maxQueue, CenterSystem centerSystem) {
        super(barrierName, maxQueue, centerSystem);
    }
    
    @Override
    void simulate(Car car)
    {
        try {
            System.out.println(barrierName + ": Car" + car.id + " is taking ticket.");
            Thread.sleep((int)(Math.random() * (5000 - 1000 + 1)) + 1000);
            System.out.println(barrierName + ": Car" + car.id + " is enter to car park.");
            centerSystem.recordEntryTime(car);
            carEntered++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
