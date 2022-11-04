/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author Mostafa
 */
public class TableHeader extends AbstractTableModel {

    ArrayList<HeaderInvoice> headers = new ArrayList<>();
    private String[] cols = {"Invoice Num", "Invoice Date", "Customer Name", "Invoice Total"};

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HeaderInvoice header = headers.get(rowIndex);
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

    public ArrayList<HeaderInvoice> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<HeaderInvoice> headers) {
        this.headers = headers;
    }

    @Override
    public int getRowCount() {
        return headers.size();
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

}
