/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Proc;
import bean.ProcSJF;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import library.LibraryDimension;

/**
 *
 * @author Hi
 */
public class ControllerSJF {

    private JTextArea ta;
    private JTextField tfSum, tfAver;
    private String kq = "";
    private double sum=0;

    public ControllerSJF(JTextArea ta, JTextField tfSum, JTextField tfAver) {
        this.ta = ta;
        this.tfSum = tfSum;
        this.tfAver = tfAver;
    }
    public void reset(){
        kq="";
        sum=0;
    }
    public Vector getCols(int ckModel) {
        Vector v = new Vector();
        v.add("Id");

        v.add("Burst Time");
        if (ckModel == 2) {
            v.add("Arrival Time");
        }
        if (ckModel == 1) {
            v.add("Priority");
            v.add("TimeWaiting");
        }
         if (ckModel == 3) {
            v.add("TimeWaiting");
        }

        return v;
    }

    public Vector getRows(ArrayList<ProcSJF> listItem, int ckModel) {
        Vector v = new Vector();
        int p = 1;
        for (ProcSJF pro : listItem) {
            Vector v1 = new Vector();
            v1.add(pro.getId());
            v1.add(pro.getBurstTime());
            if (ckModel == 2) {
                v1.add(pro.getArrivalTime());
                
            }
            if (ckModel == 1) {
                v1.add(p);
                v1.add(pro.getTimeWaiting());
                p++;
            }
            if (ckModel == 3) {
                v1.add(pro.getTimeWaiting());
            }
            v.add(v1);
        }

        return v;
    }

    public void loadTable(JTable tb, DefaultTableModel model, ArrayList<ProcSJF> listItem, int ckModel) {
        model.setDataVector(getRows(listItem, ckModel), getCols(ckModel));
        tb.setModel(model);
        setWidthHeightTable(tb, ckModel);
    }

    public void setWidthHeightTable(JTable table, int ckModel) {
        table.getTableHeader().setPreferredSize(new Dimension(table.getPreferredSize().width, 26));
        table.setRowHeight(26);
        table.getColumnModel().getColumn(1).setPreferredWidth(LibraryDimension.FCFS_HEAD_WIDTH_COL_ID);
        if (ckModel == 1) {
            table.getColumnModel().getColumn(2).setPreferredWidth(LibraryDimension.FCFS_HEAD_WIDTH_COL_TIMERUN);
        }
    }

    public ArrayList<ProcSJF> getListTableTT(JTable tbFCFSTT) {
        ArrayList<ProcSJF> alItem = new ArrayList<>();
        int countRow = tbFCFSTT.getRowCount();
        for (int i = 0; i < countRow; i++) {
            ProcSJF sjf = new ProcSJF((int) tbFCFSTT.getValueAt(i, 0), (double) tbFCFSTT.getValueAt(i, 1));
            alItem.add(sjf);
        }
        return alItem;
    }

    public ArrayList<ProcSJF> getListTableTTSRT(JTable tbFCFSTT) {
        ArrayList<ProcSJF> alItem = new ArrayList<>();
        int countRow = tbFCFSTT.getRowCount();
        for (int i = 0; i < countRow; i++) {
            ProcSJF sjf = new ProcSJF((int) tbFCFSTT.getValueAt(i, 0), (double) tbFCFSTT.getValueAt(i, 2), (double) tbFCFSTT.getValueAt(i, 1));
            alItem.add(sjf);
        }
        return alItem;
    }

//    public ArrayList<Proc> editSJFTT(Proc item, JTable tb) {
//        ArrayList<Proc> alItem = getListTableTT(tb);
//        for (Proc sjf : alItem) {
//            if (sjf.getId() == item.getId()) {
//                sjf.setBurstTime(item.getBurstTime());
//                break;
//            }
//        }
//        return alItem;
//    }
//
//    public ArrayList<Proc> deleteSJFTT(Proc item, JTable tb) {
//        ArrayList<Proc> alItem = getListTableTT(tb);
//        int idProc = 0;
//        int ckLoop = 0;
//        for (Proc proc : alItem) {
//            if (proc.getId() == item.getId()) {
//                idProc = proc.getId();
//                ckLoop = 1;
//                alItem.remove(proc);
//            }
//        }
//
//        return alItem;
//    }
//
    public ArrayList<ProcSJF> ketQuaSJF(JTable tb,ArrayList<ProcSJF> alItem1) {
        ArrayList<ProcSJF> alItem = sxbyP(alItem1);
        int stt = 1;
        int timeWaiting = 0;
        for (Proc proc : alItem) {
            proc.setTimeWaiting(timeWaiting);
            kq += "Waiting time for job " + stt + " : " + proc.getTimeWaiting() + "unit    JOB" + stt + " [" + proc.getTimeWaiting() + "," + proc.getBurstTime() + "]" + "\n";
            sum += proc.getTimeWaiting();
            stt++;
            timeWaiting += proc.getBurstTime();
        }
        ta.setText(kq);
        tfSum.setText(String.valueOf(sum));
        tfAver.setText(String.valueOf((double) sum / alItem.size()));
        return alItem;
    }

    public ArrayList<ProcSJF> sxbyP(ArrayList<ProcSJF> alItem) {
        ProcSJF tmp1;
        ArrayList<ProcSJF> alProc;
        for (int i = 0; i < alItem.size() - 1; i++) {
            for (int j = i + 1; j < alItem.size(); j++) {
                if (alItem.get(i).getBurstTime() > alItem.get(j).getBurstTime()) {
                    tmp1 = alItem.get(i);
                    alItem.set(i, alItem.get(j));
                    alItem.set(j, tmp1);
                }
            }

        }
        alProc = alItem;
        return alItem;
    }

    public ArrayList<ProcSJF> sxByArrivalTime(ArrayList<ProcSJF> alItem) {
        ProcSJF tmp1;
        ArrayList<ProcSJF> alProc;
        //sx tu tien trinh thu 2
        for (int i = 0; i < alItem.size() - 1; i++) {
            for (int j = i + 1; j < alItem.size(); j++) {
                if (alItem.get(i).getArrivalTime() > alItem.get(j).getArrivalTime()) {
                    tmp1 = alItem.get(i);
                    alItem.set(i, alItem.get(j));
                    alItem.set(j, tmp1);
                }
            }

        }
        alProc = alItem;
        return alItem;
    }

    public ArrayList<ProcSJF> ketQuaSRT(JTable tb) {
        ArrayList<ProcSJF> alItem1= sxByArrivalTime((ArrayList<ProcSJF>) getListTableTT(tb));
        ArrayList<ProcSJF> alItem2=alItem1;
        int stt = 1;
        double time = 0;
        for (ProcSJF proc : alItem1) {
            double timeWaiting = 0;
            ArrayList<ProcSJF> alItem=getListNotProc(alItem1, proc);
            if(alItem.size()>0){
                
                ProcSJF p = new ProcSJF();
                 while (time <p.getArrivalTime()) {
                    if ((time+proc.getBurstTime())<= p.getArrivalTime()) {
                        proc.setBurstTime(0);
                        proc.setArrivalTime(0);
                        timeWaiting += proc.getBurstTime();
                        updateWaitTime(timeWaiting, alItem1, proc,time);
                        time += proc.getBurstTime();
                        proc.setArrivalTime(-1);
                    } else {
                        proc.setBurstTime(proc.getBurstTime()-(p.getArrivalTime()-time));
                        timeWaiting +=p.getArrivalTime()-time;
                        updateWaitTime(timeWaiting, alItem1, proc,time);
                        proc.setArrivalTime(-1);
                        time = p.getArrivalTime();
                    }
                }
            }else{
            
                
                
            }
        }
//        ta.setText(kq);
//        tfSum.setText(String.valueOf(sum));
//        tfAver.setText(String.valueOf((double) sum / alItem.size()));
        return alItem1;
    }

    public void updateWaitTime(double timeWaiting, ArrayList<ProcSJF> alItem, ProcSJF proc,double time) {
        for (ProcSJF p : alItem) {
            if (p.getBurstTime() > 0 && p.getId() != proc.getId()&&p.getArrivalTime()<time) {
                p.setTimeWaiting(p.getTimeWaiting() + timeWaiting);
            }
        }
    }

    public ArrayList<ProcSJF> getListNotProc(ArrayList<ProcSJF> alItem, ProcSJF proc) {
        for (ProcSJF p : alItem) {
            if (p == proc || p.getBurstTime() == 0||p.getArrivalTime()>=0) {
                alItem.remove(p);
                break;
            }
        }
        return alItem;
    }
//  public ArrayList<ProcSJF> ketQuaSRT(JTable tb) {
//        ArrayList<ProcSJF> alItem1= sxByArrivalTime((ArrayList<ProcSJF>) getListTableTT(tb));
//        ArrayList<ProcSJF> alItem=alItem1;
//
//        int stt = 1;
//        double time = 0;
//        for (ProcSJF proc : alItem) {
//            double timeWaiting = 0;
//            ProcSJF p = new ProcSJF();
////            ArrayList<ProcSJF> alItemWait = getListNotProc(alItem, proc);
////            for (ProcSJF pMin : alItemWait) {
////                if (pMin.getArrivalTime() > 0) {
////                    p = pMin;
////                    break;
////                }
////            }
//           // if (p != null) {
//                while (time <p.getArrivalTime()) {
//                    if ((time+proc.getBurstTime())<= p.getArrivalTime()) {
//                        proc.setBurstTime(0);
//                        proc.setArrivalTime(0);
//                        timeWaiting += proc.getBurstTime();
//                        updateWaitTime(timeWaiting, alItem1, proc,time);
//                        time += proc.getBurstTime();
//                        proc.setArrivalTime(-1);
//                    } else {
//                        proc.setBurstTime(proc.getBurstTime()-(p.getArrivalTime()-time));
//                        timeWaiting +=p.getArrivalTime()-time;
//                        updateWaitTime(timeWaiting, alItem1, proc,time);
//                        proc.setArrivalTime(-1);
//                        time = p.getArrivalTime();
//                       
//                        
//                    }
//                }
////            } else {
////                proc.setBurstTime(0);
////                timeWaiting += proc.getBurstTime();
////                updateWaitTime(timeWaiting, alItem1, proc,time);
////                time+=proc.getBurstTime();
////                proc.setArrivalTime(-1);
////              //  ketQuaSJF(tb, alItemWait);
////                
////            }
//        }
//        //chạy các tiến trình còn lại
//        
////        ta.setText(kq);
////        tfSum.setText(String.valueOf(sum));
////        tfAver.setText(String.valueOf((double) sum / alItem.size()));
//        return alItem1;
//    }
//    
    
    
}
