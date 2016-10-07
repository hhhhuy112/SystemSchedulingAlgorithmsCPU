/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author Hi
 */
public class ProcSJF extends Proc{
    private double arrivalTime;

    public ProcSJF() {
    }

    public ProcSJF(int id, double burstTime,double arrivalTime) {
        super(id, burstTime);
    }

    public ProcSJF(int id, double burstTime) {
        super(id, burstTime);
    }

    public ProcSJF(int id, double burstTime,double arrivalTime, double timeWaiting) {
        super(id, burstTime, timeWaiting);
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
}
