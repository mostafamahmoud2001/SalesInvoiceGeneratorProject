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
public class HeaderTable extends AbstractTableModel {

    ArrayList<InvoiceHeader> headers = new ArrayList<>();
    private String[] coluNames = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};

    public ArrayList<InvoiceHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<InvoiceHeader> headers) {
        this.headers = headers;
    }

    @Override
    public int getRowCount() {
        return headers.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader header = headers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return header.getInvoiceNumber();
            case 1:
                return header.getInvoiceDate();
            case 2:
                return header.getCustomerName();
            case 3:
                return header.getInvoiceTotal();

        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return coluNames[column];
    }

}
