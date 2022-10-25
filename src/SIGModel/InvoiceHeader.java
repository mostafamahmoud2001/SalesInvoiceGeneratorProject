/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class InvoiceHeader {

    private int invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private double invoiceTotal;
    ArrayList<InvoiceLine> lines;
    private  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    public ArrayList<InvoiceLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public InvoiceHeader(int invoiceNumber, Date invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = formatter.format(invoiceDate);
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal += invoiceTotal;
    }
@Override
    public String toString() {
        return invoiceNumber+ "," + (invoiceDate) + "," +customerName  ;
    }

    public InvoiceHeader() {
        
    }

}
