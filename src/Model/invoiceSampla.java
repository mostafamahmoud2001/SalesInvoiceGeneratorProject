/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Mostafa
 */
public class invoiceSampla {

    private int invoiceNum;
    private double lineTotal;
    private int itemCount;
    private String itemName;
    private double itemprice;
    private HeaderInvoice header;

    public invoiceSampla(int invoiceNumber, String customerName, double itemprice, int itemCount, HeaderInvoice header) {
        this.invoiceNum = invoiceNumber;
        this.itemName = customerName;
        this.itemprice = itemprice;
        this.itemCount = itemCount;
        this.lineTotal = itemprice * itemCount;
        this.header = header;
    }

    public void setHeader(HeaderInvoice header) {
        this.header = header;
    }

    public double getLineTot() {
        return lineTotal;
    }

    public int getItemCount() {
        return itemCount;
    }

    public HeaderInvoice getHeader() {
        return header;
    }

    public void setItemprice(double itemprice) {
        this.itemprice = itemprice;
    }

    public void setInvNumber(int invoiceNumber) {
        this.invoiceNum = invoiceNumber;
    }

    public int getInvNumber() {
        return invoiceNum;
    }

    public String getitemName() {
        return itemName;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }

    public double getItemprice() {
        return itemprice;
    }

    @Override
    public String toString() {
        return invoiceNum + "," + itemName + "," + itemprice + "," + itemCount;
    }

    public void setitemName(String customerName) {
        this.itemName = customerName;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

}
