/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author dougl
 */
public class CarPark {
    int numCarPark;
    protected int spaces;
    protected int capacity;

    CarPark(int n) {
        capacity = spaces = n;
        numCarPark = 0;
    }

    synchronized BarrierScenario park(Car car, long timeoutMillis) throws InterruptedException {
        long endTime = System.currentTimeMillis() + timeoutMillis;
        long remaining = timeoutMillis;
        while (spaces == 0 && remaining > 0) {
            wait(remaining);
            remaining = endTime - System.currentTimeMillis();
        }
        if (spaces == 0)
        {
            return BarrierScenario.FULL;
        }
        --spaces;
        numCarPark++;
        System.out.println("CarPark: Car" + car.id + " parked. (" + spaces + " slots remaining)");
        notifyAll();
        return BarrierScenario.PARKED;
    }

    synchronized void moveOut(Car car) throws InterruptedException{
        while (spaces==capacity) wait();
        ++spaces;
        System.out.println("CarPark: Car" + car.id + " moved out. (" + spaces + " slots remaining)");
        notify();
     }
}
