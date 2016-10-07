/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Proc;
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
public class ControllerRR {
      private JTextArea ta;
    private JTextField tfSum,tfAver;

    public ControllerRR(JTextArea ta, JTextField tfSum, JTextField tfAver) {
        this.ta = ta;
        this.tfSum = tfSum;
        this.tfAver = tfAver;
    }
    public Vector getCols(int ckModel) {
        Vector v = new Vector();
        v.add("Id");
        v.add("Burst Time");
        if (ckModel == 1) {
            v.add("TimeWaiting");
        }
        return v;
    }
    
    public Vector getRows(ArrayList<Proc> listItem, int ckModel) {
        Vector v = new Vector();
        for (Proc fcfs : listItem) {
            Vector v1 = new Vector();
            v1.add(fcfs.getId());
            v1.add(fcfs.getBurstTime());
            if (ckModel == 1) {
                v1.add(fcfs.getTimeWaiting());
            }
            v.add(v1);
        }
        
        return v;
    }
    
    public void loadTable(JTable tb, DefaultTableModel model, ArrayList<Proc> listItem, int ckModel) {
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
    
    public ArrayList<Proc> getListTableTT(JTable tbRRTT) {
        ArrayList<Proc> alItem = new ArrayList<>();
        int countRow = tbRRTT.getRowCount();
        for (int i = 0; i < countRow; i++) {
            Proc fcfs = new Proc((int) tbRRTT.getValueAt(i, 0), (double) tbRRTT.getValueAt(i, 1), 0);
            alItem.add(fcfs);
        }
        return alItem;
    }
    
    public ArrayList<Proc> editRRTT(Proc fcfs, JTable tbRRTT) {
        ArrayList<Proc> alItem = getListTableTT(tbRRTT);
        for (Proc fcfs1 : alItem) {
            if (fcfs1.getId() == fcfs.getId()) {
                fcfs1.setBurstTime(fcfs.getBurstTime());
                break;
            }
        }
        return alItem;
    }
    
    public ArrayList<Proc> deleteRRTT(Proc proc, JTable tbRRTT) {
        ArrayList<Proc> alItem = getListTableTT(tbRRTT);
        for (Proc proc1 : alItem) {
            if (proc1.getId() == proc.getId()) {
                alItem.remove(proc1);
            }
        }
        
        return alItem;
    }
    
    public ArrayList<Proc> ketQua(JTable tbRRTT, int quantum) {
        ArrayList<Proc> alItem = getListTableTT(tbRRTT);
        int countRound = getCountRound(tbRRTT, quantum);
        String kq="";
        double sum=0;
        for (int i = 1; i <= countRound; i++) {
            kq+="Round"+i+"\n";
            for (Proc p : alItem) {
                double timeW = 0;
                double timeP = p.getBurstTime();
                if (timeP > 0) {
                    
                    p.setBurstTime(timeP - quantum);
                    if (p.getBurstTime() <= 0) {
                        p.setBurstTime(0);
                        kq+=" JOB"+p.getId() + " is completed "+"\n";
                        System.out.println(p.getId() + "job is completed ");
                        
                        timeW = timeP;
                    } else {
                        timeW = quantum;
                        System.out.println(p.getId() + " job remaining time is " + p.getBurstTime());
                        kq +=" JOB"+String .valueOf(p.getId()) +" remaining time is " +String.valueOf(p.getBurstTime())+"\n";
                    }
                    
                }
                sum+=timeW;
                upTime(alItem, timeW, p);
                
            }
        }
        ta.setText(kq);
        tfSum.setText(String.valueOf(sum));
        tfAver.setText(String.valueOf(sum/alItem.size()));
        return setTimeWait(tbRRTT, alItem);
    }
     public void upTime(ArrayList<Proc> alItem, double time, Proc p1) {
        for (Proc p : alItem) {
            if (p.getId() != p1.getId() && p.getBurstTime() > 0) {
                p.setTimeWaiting(p.getTimeWaiting() + time);
            }
            System.out.println(p.getId()+":"+p.getTimeWaiting());
        }
    }
    public ArrayList<Proc> setTimeWait(JTable tbRRTT, ArrayList<Proc> alItem1) {
        ArrayList<Proc> alItem = getListTableTT(tbRRTT);
        for (Proc p : alItem) {
            for (Proc p1 : alItem1) {
                if (p.getId() == p1.getId()) {
                    p.setTimeWaiting(p1.getTimeWaiting());
                    
                }
            }            
        }
        return alItem;
    }

   
    
    public int getCountRound(JTable tbRRTT, int quantum) {
        int r = 0;
        ArrayList<Proc> alItem = getListTableTT(tbRRTT);
        double max = alItem.get(0).getBurstTime();
        for (Proc proc : alItem) {
            if (max <= proc.getBurstTime()) {
                max = proc.getBurstTime();
            }
        }
        if ((max % quantum) == 0) {
            r = (int) (max / quantum);
        } else {
            r = (int) (max / quantum) + 1;
        }
        return r;
    }
    //    public ArrayList<Proc> ketQua(JTable tbFCFSTT, int quantum) {
//        ArrayList<Proc> alItem1 = getListTableTT(tbFCFSTT);
//        
//        int timeWaiting = 0;
//        for (Proc p1 : alItem1) {
//            int timeW = timeWaiting;
//            while (p1.getTimeRunning() > 0) {
//                if (p1.getTimeRunning() > quantum) {
//                    p1.setTimeRunning(p1.getTimeRunning() - quantum);
//                    ArrayList<Proc> alItem2 = getListTableTT(tbFCFSTT);
//                    alItem2.remove(p1);
//                    for (Proc p2 : alItem2) {
//                        if (p2.getTimeRunning() > quantum) {
//                            timeW += quantum;
//                        } else {
//                            timeW += p2.getTimeRunning();
//                        }
//                    }
//                    p1.setTimeWaiting(timeW);
//                    timeWaiting += p1.getTimeRunning();
//                } else {
//                    p1.setTimeWaiting(timeW);
//                    timeWaiting += p1.getTimeRunning();
//                    break;
//                }
//            }
//        }
//        return alItem1;
//    }
}
