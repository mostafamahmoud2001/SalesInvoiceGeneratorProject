/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.text.*;
import java.util.*;

/**
 *
 * @author Mostafa
 */
public class HeaderInvoice {

    private int invoiceNumber;
    private String invoiceDate;
    private String customerName;
    private double invoiceTotal;
    private final SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
    
    
    ArrayList<invoiceSampla> lines;
    public ArrayList<invoiceSampla> getLines() {
        return lines;
    }
    public void setLines(ArrayList<invoiceSampla> lines) {
        this.lines = lines;
    }

    public HeaderInvoice(int invoiceNumber, Date invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = form.format(invoiceDate);
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

    public  String getInvoiceDate() {
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

   
}
