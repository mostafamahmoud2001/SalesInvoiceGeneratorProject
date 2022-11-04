
package SIGController;
import Model.*;
import SIGView.MainFrame;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
            HeaderInvoice invoiceHeader = mainFrame.getHeadersArray().get(selectedRow);
            int invoiceNum = invoiceHeader.getInvoiceNumber();
            String customerName = invoiceHeader.getCustomerName();
            String invoiceDate = invoiceHeader.getInvoiceDate();
            mainFrame.setInvoiceNumLblText(Integer.toString(invoiceNum));
            mainFrame.setInvoiceDateLblText(invoiceDate);
            mainFrame.setInvoiceTotalLblText(Double.toString(invoiceHeader.getInvoiceTotal()));
            mainFrame.setCustomerNameLblText(customerName);
            ArrayList<invoiceSampla> lines = invoiceHeader.getLines();
            if (lines == null) {
                lines = new ArrayList<>();
            }
            TableSamble lTable = new TableSamble();
            lTable.setItems(lines);
            mainFrame.getInvoiceItemsTable().setModel(lTable);

        }
    }

}
