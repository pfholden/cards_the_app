/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tantech.cards.ui;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author pfholden
 */
public class OwnedCardsModel extends AbstractTableModel{
    private String[] columnNames = {"Name", "Colors", "Text", "Mana Cost", "Card ID"};
    private Object[][] data; 

    public OwnedCardsModel(Object[][] data) {
        this.data = data;
    }
    
    public OwnedCardsModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }
    
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public void setValueAt(int row, int col, Object data) {
        this.data[row][col] = data;
        fireTableCellUpdated(row, col);
    }
    
}
