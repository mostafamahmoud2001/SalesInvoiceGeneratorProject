/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGController;

import SIGModel.InvoiceHeader;
import SIGModel.InvoiceLine;
import SIGModel.LineTable;
import SIGView.MainFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DELL
 */
public class TableSelectionController implements ListSelectionListener {

    private final MainFrame mainFrame;

    public TableSelectionController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
     //   int selectedRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedRow = mainFrame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            InvoiceHeader invoiceHeader = mainFrame.getHeadersArray().get(selectedRow);
            int invoiceNum = invoiceHeader.getInvoiceNumber();
            String customerName = invoiceHeader.getCustomerName();
            String invoiceDate = invoiceHeader.getInvoiceDate();
            mainFrame.setInvoiceNumLblText(Integer.toString(invoiceNum));
            mainFrame.setInvoiceDateLblText(invoiceDate);
            mainFrame.setInvoiceTotalLblText(Double.toString(invoiceHeader.getInvoiceTotal()));
            mainFrame.setCustomerNameLblText(customerName);
            ArrayList<InvoiceLine> lines = invoiceHeader.getLines();
            if (lines == null) {
                lines = new ArrayList<>();
            }
            LineTable lTable = new LineTable();
            lTable.setItems(lines);
           // MainFrame.invoiceItems.setModel(lFrame);
             mainFrame.getInvoiceItemsTable().setModel(lTable);

        }
    }

}
