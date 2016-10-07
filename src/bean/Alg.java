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
public class Alg {
    private String kq;
    private double sumTime;
    private double averTime;

    public Alg(String kq, double sumTime, double averTime) {
        this.kq = kq;
        this.sumTime = sumTime;
        this.averTime = averTime;
    }

    public String getKq() {
        return kq;
    }

    public void setKq(String kq) {
        this.kq = kq;
    }

    public double getSumTime() {
        return sumTime;
    }

    public void setSumTime(double sumTime) {
        this.sumTime = sumTime;
    }

    public double getAverTime() {
        return averTime;
    }

    public void setAverTime(double averTime) {
        this.averTime = averTime;
    }
    
}
