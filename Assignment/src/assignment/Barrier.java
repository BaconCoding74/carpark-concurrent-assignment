/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dougl
 */
public class Barrier {
    String barrierName;
    CenterSystem centerSystem;
    int maxQueue;
    boolean maintenance;
    boolean workerArrived;
    final Object queueLock = new Object();
    final Queue<Integer> waitingQueue = new LinkedList<>();
    
    Barrier(String barrierName, int maxQueue, CenterSystem centerSystem)
    {
        this.barrierName = barrierName;
        this.maxQueue = maxQueue;
        this.maintenance = false;
        this.centerSystem = centerSystem;
    }
    
    void simulate(Car car)
    {
        System.out.println(barrierName + ": Car" + car.id + " is simulating.");
    }
    
    public void workerArrive() {
        synchronized (queueLock) {
            if (maintenance) {
                workerArrived = true;
                System.out.println(barrierName + ": Worker has arrived for manual intervention and reparation.");
                queueLock.notifyAll();
            }
        }
    }
    
    public BarrierScenario request(Car car, int patientTimeSeconds)
    {
        // Calculate the deadline
        long deadline = System.currentTimeMillis() + patientTimeSeconds * 1000L;
        
        // Lock when adding queue
        synchronized (queueLock) {
            
            // Check if full
            if (waitingQueue.size() > maxQueue) {
                System.out.println("Car" + car.id + ": Queue at " + barrierName + " is full.");
                return BarrierScenario.FULL;
            }
            
            // Add to queue
            waitingQueue.add(car.id);
            System.out.println("Car" + car.id + ": Queue at " + barrierName + ".");
            
            while ((maintenance && !workerArrived) || waitingQueue.peek() != car.id) {
                long waitTime = deadline - System.currentTimeMillis();
                if (waitTime <= 0) {
                    waitingQueue.remove(car.id);
                    queueLock.notifyAll();
                    System.out.println("Car" + car.id + ": Left queue at " + barrierName + " due to impatience.");
                    return BarrierScenario.IMPATIENT;
                }
                try {
                    queueLock.wait(waitTime);
                } catch (InterruptedException e) {
                    waitingQueue.remove(car.id);
                    queueLock.notifyAll();
                    Thread.currentThread().interrupt();
                    System.out.println("Car" + car.id + ": Interrupted at " + barrierName + ".");
                    return BarrierScenario.INTERRUPT;
                }
            }
        }
        
        this.simulate(car);

        // Next car turn
        synchronized (queueLock) {
            if (Math.floor(Math.random() * 101) >= 80)
            {
                maintenance = true;
                System.out.println(barrierName + ": Need maintenance.");
                Thread worker = new Thread(new Worker(this));
                worker.start();
            }
            
            waitingQueue.poll();
            queueLock.notifyAll();
            return BarrierScenario.SUCCESS;
        }  
    }
}
