/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGModel;

/**
 *
 * @author DELL
 */
public class InvoiceLine {

    private int invoiceNum;
    private String itemName;
    private double itemprice;
    private int itemCount;
    private double lineTotal;
    private InvoiceHeader header;

    public InvoiceLine(int invoiceNumber, String customerName, double itemprice, int itemCount, InvoiceHeader header) {
        this.invoiceNum = invoiceNumber;
        this.itemName = customerName;
        this.itemprice = itemprice;
        this.itemCount = itemCount;
        this.lineTotal = itemprice * itemCount;
        this.header = header;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public String getitemName() {
        return itemName;
    }

    public int getInvoiceNumber() {
        return invoiceNum;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNum = invoiceNumber;
    }

    public double getItemprice() {
        return itemprice;
    }

    public void setitemName(String customerName) {
        this.itemName = customerName;
    }

    @Override
    public String toString() {
        return invoiceNum + "," + itemName + "," + itemprice + "," + itemCount;
    }

    public void setItemprice(double itemprice) {
        this.itemprice = itemprice;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }

}
