/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dougl
 */
public class CenterSystem {
    CarParkEntry[] entries;
    CarParkExit[] exits;
    CarPark carPark;
    Clock clock;
    boolean open;
    boolean fullyClosed;
    int closeHour;
    
    CenterSystem(int numEntries, int numExits, int capacity)
    {
        closeHour = 23;
        
        open = true;
        entries = new CarParkEntry[numEntries];
        exits = new CarParkExit[numExits];
        carPark = new CarPark(capacity);
        
        for (int index = 0; index < numEntries; index++) {
            entries[index] = new CarParkEntry("Entry" + index, 50, this);
        }
        
        for (int index = 0; index < numExits; index++) {
            exits[index] = new CarParkExit("Exit" + index, 200, this);
        }
        
        clock = new Clock(this);
    }
    
    public void startClock()
    {
        Thread ct = new Thread(clock);
        ct.start();
    }
    
    public String getParkTimeString(int entryHour, int entryMinute)
    {
        int hourDifference = clock.getHour() - entryHour;
        int minuteDifference = clock.getMinute() - entryMinute;
        
        return String.format("%d minutes", (hourDifference * 60 + minuteDifference));
    }
    
    public void recordEntryTime(Car car)
    {
        car.entryHour = clock.getHour();
        car.entryMinute = clock.getMinute();
    }
    
    public int getParkingDuration(Car car)
    {
        int hourDifference = clock.getHour() - car.entryHour;
        int minuteDifference = clock.getMinute() - car.entryMinute;
        
        return (hourDifference * 60 + minuteDifference);
    }
    
    public int getParkingDuration(int entryHour, int entryMinute)
    {
        int hourDifference = clock.getHour() - entryHour;
        int minuteDifference = clock.getMinute() - entryMinute;
        
        return (hourDifference * 60 + minuteDifference);
    }
    
    public double calculatePrice(int entryHour, int entryMinute)
    {
        double price;
        int duration = getParkingDuration(entryHour, entryMinute);
        
        if (duration <= 60)
        {
            price = 0;
        }
        else if (!open)
        {
            price = (duration) * 3;
        }
        else
        {
            price = (duration) * 0.05;
        }
        
        return price;
    }
    
    public void closing()
    {
        System.out.println("Car Park: Reach closing time.");
        open = false;
        boolean maintenance = true;
        boolean onGoing = true;
        
        
        while (carPark.spaces != carPark.capacity || maintenance || onGoing)
        {
            maintenance = false;
            onGoing = false;
            
            for (CarParkEntry entry : entries) {
                if (!entry.waitingQueue.isEmpty())
                {
                    onGoing = true;
                    break;
                }
                if (entry.maintenance)
                {
                    maintenance = true;
                    break;
                }
            }

            for (CarParkExit exit : exits) {
                if (!exit.waitingQueue.isEmpty())
                {
                    onGoing = true;
                    break;
                }
                if (exit.maintenance)
                {
                    maintenance = true;
                    break;
                }
            }
            
            try {
                if (carPark.spaces != carPark.capacity)
                {
                    System.out.println("Car Park: Wait for all car going out with fine.");
                }
                else if (maintenance)
                {
                    System.out.println("Worker: I'm still working.");
                }
                else if (onGoing)
                {
                    System.out.println("Car Park: Still have car is exiting.");
                }
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CenterSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fullyClosed = true;
        System.out.println("System: Completely clear. Summarizing");
        printSummary();
    }
    
    public void printSummary()
    {
        int totalCarEntered = 0;
        double totalEarn = 0;
        int totalParkingDuration = 0;
        
        for (CarParkEntry entry : entries) {
            totalCarEntered += entry.carEntered;
        }
        
        for (CarParkExit exit : exits) {
            totalEarn += exit.earn;
            totalParkingDuration += exit.totalParkingDuration;
        }
        
        System.out.println();
        System.out.println("-- Summary --");
        System.out.println("Total car entered: " + totalCarEntered);
        System.out.println("Total car parked: " + carPark.numCarPark);
        System.out.println("Average parking duration: " + (totalParkingDuration/totalCarEntered) + " minutes");
        System.out.println("Total earn: RM" + totalEarn);
    }
    
    public CarParkEntry getRandomCarParkEntry()
    {
        Random r = new Random();
        return entries[r.nextInt(entries.length)];
    }
    
    public CarParkExit getRandomCarParkExit()
    {
        Random r = new Random();
        return exits[r.nextInt(entries.length)];
    }
}
