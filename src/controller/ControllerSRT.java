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
import javax.swing.table.DefaultTableModel;
import library.LibraryDimension;

/**
 *
 * @author Hi
 */
public class ControllerSRT {

    public Vector getCols(int ckModel) {
        Vector v = new Vector();
        v.add("Id");
        v.add("BurstTime");
        if (ckModel == 1) {
            v.add("Priority");
            v.add("TimeWaiting");

        }
        return v;
    }

    public Vector getRows(ArrayList<Proc> listItem, int ckModel) {
        Vector v = new Vector();
        int p = 1;
        for (Proc pro : listItem) {
            Vector v1 = new Vector();
            v1.add(pro.getId());
            v1.add(pro.getBurstTime());
            if (ckModel == 1) {
                v1.add(p);
                v1.add(pro.getTimeWaiting());
                p++;
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

    public ArrayList<Proc> getListTableTT(JTable tbSRTTT) {
        ArrayList<Proc> alItem = new ArrayList<>();
        int countRow = tbSRTTT.getRowCount();
        for (int i = 0; i < countRow; i++) {
            Proc proc = new Proc((int) tbSRTTT.getValueAt(i, 0), (double) tbSRTTT.getValueAt(i, 1));
            alItem.add(proc);
        }
        return alItem;
    }

    public ArrayList<Proc> editSRTTT(Proc item, JTable tb) {
        ArrayList<Proc> alItem = getListTableTT(tb);
        for (Proc proc : alItem) {
            if (proc.getId() == item.getId()) {
                proc.setBurstTime(item.getBurstTime());
                break;
            }
        }
        return alItem;
    }

    public ArrayList<Proc> deleteSRTTT(Proc item, JTable tb) {
        ArrayList<Proc> alItem = getListTableTT(tb);
        int idProc = 0;
        int ckLoop = 0;
        for (Proc proc : alItem) {
            if (proc.getId() == item.getId()) {
                idProc = proc.getId();
                ckLoop = 1;
                alItem.remove(proc);
            }
        }

        return alItem;
    }

    public ArrayList<Proc> ketQua(JTable tb) {
        ArrayList<Proc> alItem = sxbyP((ArrayList<Proc>)getListTableTT(tb));
        
        int timeWaiting = 0;
        int p = 1;
        for (Proc proc : alItem) {
            proc.setTimeWaiting(timeWaiting);
            timeWaiting += proc.getBurstTime();
        }
        return alItem;
    }
    
    public ArrayList<Proc> sxbyP(ArrayList<Proc> alItem) {
        Proc tmp1 ;
        ArrayList<Proc> alProc;
        //sx tu tien trinh thu 2
        for (int i = 0; i < alItem.size() - 1; i++) {
            for (int j = i + 1; j < alItem.size(); j++) {
                if (alItem.get(i).getBurstTime() > alItem.get(j).getBurstTime()) {
                    tmp1 = alItem.get(i);
                    alItem.set(i,alItem.get(j));
                    alItem.set(j, tmp1);
                }
            }
         
        }
        alProc=alItem;
        return alItem;
    }

}
