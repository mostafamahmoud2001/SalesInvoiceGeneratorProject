/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Mostafa
 */
import java.util.*;
import javax.swing.table.*;

public class TableSamble extends AbstractTableModel {

    private String[] columns = {"Invoice Num", "Item Name", "Item Price", "Item Count", "Item Total"};

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    ArrayList<invoiceSampla> items = new ArrayList<>();

    public ArrayList<invoiceSampla> getItems() {
        return items;
    }

    public void setItems(ArrayList<invoiceSampla> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        invoiceSampla line = items.get(rowIndex);
        if (columnIndex == 0) {
            return line.getInvNumber();
        } else if (columnIndex == 4) {
            return line.getLineTot();
        } else if (columnIndex == 2) {
            return line.getItemprice();
        } else if (columnIndex == 3) {
            return line.getItemCount();
        } else if (columnIndex == 1) {
            return line.getitemName();
        } else {
            return "";
        }
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

}
