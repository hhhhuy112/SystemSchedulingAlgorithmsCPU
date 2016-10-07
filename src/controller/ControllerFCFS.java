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
public class ControllerFCFS {
    private JTextArea ta;
    private JTextField tfSum,tfAver;

    public ControllerFCFS(JTextArea ta, JTextField tfSum, JTextField tfAver) {
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

    public ArrayList<Proc> getListTableTT(JTable tbFCFSTT) {
        ArrayList<Proc> alItem = new ArrayList<>();
        int countRow = tbFCFSTT.getRowCount();
        for (int i = 0; i < countRow; i++) {
            Proc fcfs = new Proc((int) tbFCFSTT.getValueAt(i, 0), (double) tbFCFSTT.getValueAt(i, 1));
            alItem.add(fcfs);
        }
        return alItem;
    }

    public ArrayList<Proc> editFCFSTT(Proc fcfs, JTable tbFCFSTT) {
        ArrayList<Proc> alItem = getListTableTT(tbFCFSTT);
        for (Proc fcfs1 : alItem) {
            if (fcfs1.getId() == fcfs.getId()) {
                fcfs1.setBurstTime(fcfs.getBurstTime());
                break;
            }
        }
        return alItem;
    }

    public ArrayList<Proc> deleteFCFSTT(Proc fcfs, JTable tbFCFSTT) {
        ArrayList<Proc> alItem = getListTableTT(tbFCFSTT);
        int idFCFS = 0;
        int ckLoop = 0;
        for (Proc fcfs1 : alItem) {
            if (fcfs1.getId() == fcfs.getId()) {
                idFCFS = fcfs.getId();
                ckLoop = 1;
                alItem.remove(fcfs1);
            }
        }
         
        return alItem;
    }

    public ArrayList<Proc> ketQua(JTable tbFCFSTT) {
        ArrayList<Proc> alItem = getListTableTT(tbFCFSTT);
        int timeWaiting = 0;
        double sum = 0;
        String kq="";
        int stt=1;
        for (Proc proc : alItem) {
            proc.setTimeWaiting(timeWaiting);
            kq+="Waiting time for job "+stt+" : "+ proc.getTimeWaiting()+"unit    JOB"+stt+" ["+proc.getTimeWaiting()+","+proc.getBurstTime()+"]"+"\n";
            sum += proc.getTimeWaiting();
            stt++;
            timeWaiting += proc.getBurstTime();
        }
        ta.setText(kq);
        tfSum.setText(String.valueOf(sum));
        tfAver.setText(String.valueOf((double) sum / alItem.size()));
        return alItem;
    }
   

}
