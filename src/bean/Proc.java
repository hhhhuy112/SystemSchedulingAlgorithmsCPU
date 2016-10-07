/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author MINHHUY HO
 */
public class Proc {
    private int id;
    private double burstTime;
    private double timeWaiting;

    public Proc() {
    }

    public Proc(int id, double burstTime) {
        this.id = id;
        this.burstTime = burstTime;
    }

    public Proc(int id, double burstTime, double timeWaiting) {
        this.id = id;
        this.burstTime = burstTime;
        this.timeWaiting = timeWaiting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public double getTimeWaiting() {
        return timeWaiting;
    }

    public void setTimeWaiting(double timeWaiting) {
        this.timeWaiting = timeWaiting;
    }

   
    

   
}
