/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment;

/**
 *
 * @author dougl
 */
public class Assignment {

    public static void main(String[] args) throws InterruptedException {
        CenterSystem c = new CenterSystem(4, 4, 50);
        c.startClock();
        
        for (int i = 1; i < 150; i++)
        {
            Car car = new Car(i, c);
            Thread thread = new Thread(car);
            thread.start();
        }
    }
}
