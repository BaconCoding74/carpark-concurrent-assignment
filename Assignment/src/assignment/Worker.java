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
public class Worker implements Runnable {
    Barrier barrier;
    
    Worker (Barrier barrier)
    {
        this.barrier = barrier;
    }
    
    @Override
    public void run()
    {
        try {
            System.out.println("Workers: Going to " + barrier.barrierName + ".");
            Thread.sleep(3000);
            
            barrier.workerArrived = true;
            System.out.println("Workers: Arrived at " + barrier.barrierName + " manual operation and maintenance is performed.");
            synchronized (barrier.queueLock) {
                barrier.queueLock.notifyAll();
            }
            Thread.sleep(4000);
            
            System.out.println("Workers: " + barrier.barrierName + " is fully repaired. Ciao");
            barrier.maintenance = false;
            barrier.workerArrived = false;
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
