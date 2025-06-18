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
public class Clock implements Runnable {
    private int hour;
    private int minute;
    CenterSystem centerSystem;
    
    Clock(CenterSystem centerSystem)
    {
        hour = 7;
        minute = 0;
        this.centerSystem = centerSystem;
    }
    
    public int getHour()
    {
        return hour;
    }
    
    public int getMinute()
    {
        return minute;
    }
    
    public void printTime()
    {
        String output = String.format("Clock: Current time is %d:%02d", hour, minute);
        System.out.println(output);
    }
    
    @Override
    public void run()
    {
        while (!centerSystem.fullyClosed)
        {
            try {
                Thread.sleep(500);
                minute += 5;
                
                if (minute >= 60)
                {
                    hour++;
                    minute-= minute;
                }
                
                if (hour >= centerSystem.closeHour)
                {
                    centerSystem.closing();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
