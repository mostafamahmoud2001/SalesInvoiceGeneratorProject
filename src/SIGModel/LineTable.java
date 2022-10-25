/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGModel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class LineTable extends AbstractTableModel {

    private String[] columnNames = {"Invoice Num", "Item Name", "Item Price", "Item Count", "Item Total"};
    ArrayList<InvoiceLine> items = new ArrayList<>();

    public ArrayList<InvoiceLine> getItems() {
        return items;
    }

    public void setItems(ArrayList<InvoiceLine> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = items.get(rowIndex);
        if (columnIndex == 0)
            return line.getInvoiceNumber();
        else if(columnIndex == 4)
            return line.getLineTotal();
        else if(columnIndex == 2)
            return line.getItemprice();
        else if(columnIndex == 3)
            return line.getItemCount();
        else if(columnIndex == 1)
            return line.getitemName();
        else 
            return "";
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}
