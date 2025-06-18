/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dougl
 */
public class Car implements Runnable {
    int id;
    int entryHour;
    int entryMinute;
    boolean fine = false;
    CenterSystem centerSystem;
    
    public Car(int id, CenterSystem centerSystem)
    {
        this.id = id;
        this.centerSystem = centerSystem;
    }
    
    public int getCarID()
    {
        return id;
    }
    
    @Override
    public void run()
    {
        BarrierScenario entryResult = centerSystem.getRandomCarParkEntry().request(this, (int)(Math.random() * (30000 - 10000 + 1)) + 10000);
        if (entryResult != BarrierScenario.SUCCESS)
        {
            return;
        }
        
        try {
            BarrierScenario parkResult = centerSystem.carPark.park(this, (long)(Math.random() * (10000 - 5000 + 1)) + 5000);
            if (parkResult == BarrierScenario.PARKED)
            {
                Thread.sleep((int)(Math.random() * (50000 - 20000 + 1)) + 20000);
                centerSystem.carPark.moveOut(this);
            }
            else if (parkResult == BarrierScenario.FULL)
            {
                System.out.println("Car" + id + ": No parking in car park. Going out now.");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        centerSystem.getRandomCarParkExit().request(this, (int)(Math.random() * (1000000000 - 1000000000 + 1)) + 1000000000);
    }
}
