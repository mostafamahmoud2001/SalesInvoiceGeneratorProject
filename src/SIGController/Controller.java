/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SIGController;

import SIGModel.FileOperation;
import SIGModel.HeaderTable;
import SIGModel.InvoiceHeader;
import SIGModel.InvoiceLine;
import SIGModel.LineTable;
import SIGView.HeaderFrame;
import SIGView.LineFrame;
import SIGView.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Controller implements ActionListener {

    private final MainFrame mainFrame;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private ArrayList<InvoiceHeader> headers;
    private ArrayList<InvoiceLine> invoiceItems;

    private HeaderFrame hFrame;
    private LineFrame lFrame;
    private FileOperation fileOperation;

    public Controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void invoiceDateLblKeyPressed(java.awt.event.KeyEvent evt) {
        int Row = mainFrame.getInvoiceTable().getSelectedRow();
        if (evt.getKeyCode() == 10 && Row != -1) {
            javax.swing.JTextField x = (javax.swing.JTextField) evt.getComponent();
            try {
                Date parse = formatter.parse(x.getText());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Date Not In Formate dd-MM-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            InvoiceHeader invoiceHeader = mainFrame.getHeadersArray().get(Row);
            if (invoiceHeader.getInvoiceDate() != x.getText()) {
                invoiceHeader.setInvoiceDate(x.getText());
                HeaderTable hTable = new HeaderTable();
                hTable.setHeaders(mainFrame.getHeadersArray());
                mainFrame.getInvoiceTable().setModel(hTable);

            }
        }

    }

    public void invoiceCustomerNameLblKeyPressed(java.awt.event.KeyEvent evt) {
        int Row = mainFrame.getInvoiceTable().getSelectedRow();
        if (evt.getKeyCode() == 10 && Row != -1) {
            javax.swing.JTextField x = (javax.swing.JTextField) evt.getComponent();
            InvoiceHeader invoiceHeader = mainFrame.getHeadersArray().get(Row);
            if (invoiceHeader.getCustomerName() != x.getText()) {
                invoiceHeader.setCustomerName(x.getText());
                HeaderTable hTable = new HeaderTable();
                hTable.setHeaders(mainFrame.getHeadersArray());
                mainFrame.getInvoiceTable().setModel(hTable);
            }
        }
    }

//
    public void intiTables() throws IOException {
        fileOperation = new FileOperation(mainFrame);
        headers = new ArrayList<>();
        invoiceItems = new ArrayList<>();

        final File headerFile = new File("InvoiceHeader.csv");
        final File lineFile = new File("InvoiceLine.csv");
        System.out.println(headerFile.canRead());
        if (!headerFile.canRead() || !lineFile.canRead()) {
            JOptionPane.showMessageDialog(null, "File Not Found ", "Date Error", JOptionPane.ERROR_MESSAGE);
            throw new FileNotFoundException();

        }
        ArrayList<String> headerLines = fileOperation.readFile(headerFile);
        ArrayList<String> itemLines = fileOperation.readFile(lineFile);
        //generate (invoice number,invoice date, cusomer name) from each HeaderLine

        for (String line : headerLines) {
            String[] items = line.split(",");
            Date date = new Date();
            int invoiceNumber = Integer.parseInt(items[0]);
            try {
                date = formatter.parse(items[1]);
            } catch (ParseException parserx) {
                JOptionPane.showMessageDialog(null, "Date must be in format dd-MM-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
            }
            String customerName = items[2];
            InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumber, date, customerName);
            headers.add(invoiceHeader);
        }
        for (String line : itemLines) {
            String[] items = line.split(",");
            int invoiceNumber = Integer.parseInt(items[0]);
            String customerName = items[1];
            double price = Double.parseDouble(items[2]);
            int count = Integer.parseInt(items[3]);

            if (price < 0 || count < 0 || invoiceNumber < 0) {
                JOptionPane.showMessageDialog(null, "Wrong Data Format in File" + lineFile, "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InvoiceLine invoiceLine = null;
            for (InvoiceHeader header : headers) {
                if (invoiceNumber == header.getInvoiceNumber()) {
                    invoiceLine = new InvoiceLine(invoiceNumber, customerName, price, count, header);
                    header.setInvoiceTotal(invoiceLine.getLineTotal());
                    break;
                }
            }

            invoiceItems.add(invoiceLine);
        }
        for (InvoiceHeader header : headers) {
            ArrayList<InvoiceLine> tmp = new ArrayList<>();
            for (InvoiceLine invoiceline : invoiceItems) {
                if (header.getInvoiceNumber() == invoiceline.getInvoiceNumber()) {
                    tmp.add(invoiceline);
                }
            }
            header.setLines(tmp);
        }

        mainFrame.setHeadersArray(headers);
        mainFrame.setLinesArray(invoiceItems);
        HeaderTable hTable = new HeaderTable();
        hTable.setHeaders(mainFrame.getHeadersArray());
        mainFrame.getInvoiceTable().setModel(hTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        System.out.println(command);
        switch (command) {
            case "Create New Invoice":
                createInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Line":
                createLine();
                break;
            case "Delete Line":
                deleteLine();
                break;
            case "Load File": {
                try {
                    loadFile();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong Data Format ", "Date Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Wrong File Format ", "Date Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
            break;
            case "Save File":
                saveFile();
                break;

            case "Add Invoice":
                addInvoice();
                break;

            case "Cancel Inovice":
                cancelInvoice();
                break;

            case "Add Line":
                addLine();
                break;

            case "Cancel Line":
                cancelLine();
                break;
        }

    }

    private void createInvoice() {
        JOptionPane.showMessageDialog(null, "Enter Invoice details ", " ", JOptionPane.INFORMATION_MESSAGE);
        //to find the min Invoice id 
        int num = 1;
        int totalIDs = 0;
        for (InvoiceHeader h : headers) {
            num = Math.max(num, h.getInvoiceNumber());
            totalIDs += h.getInvoiceNumber();
        }
        int totalSum = (num * (num + 1)) / 2;
        if (totalSum != totalIDs) {
            num = totalSum - totalIDs;
        } else {
            num++;
        }
        hFrame = new HeaderFrame(mainFrame);
        hFrame.setInvoiceNumber(Integer.toString(num));

    }

    private void createLine() {
        //   int selectedRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedRow = mainFrame.getInvoiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice Header to add items on it ", "Insertion Failure", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Enter Line  details ", " ", JOptionPane.INFORMATION_MESSAGE);
            lFrame = new LineFrame(mainFrame);

        }
    }

    private void deleteInvoice() {
        // int selectedRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedRow = mainFrame.getInvoiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice Header to delete it ", "delete erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            ArrayList<InvoiceLine> emptyLines = new ArrayList<>();
            mainFrame.getHeadersArray().get(selectedRow).setLines(emptyLines);

            LineTable lTable = new LineTable();
            lTable.setItems(mainFrame.getHeadersArray().get(selectedRow).getLines());
            //MainFrame.invoiceItems.setModel(lTable);
            mainFrame.getInvoiceItemsTable().setModel(lTable);
            mainFrame.setCustomerNameLblText(",");
            mainFrame.setInvoiceDateLblText(",");
            mainFrame.setInvoiceNumLblText(",");
            mainFrame.setInvoiceTotalLblText("0");

            mainFrame.getHeadersArray().remove(selectedRow);
            HeaderTable hTable = new HeaderTable();
            hTable.setHeaders(mainFrame.getHeadersArray());
            //        MainFrame.invoiceTable.setModel(hTable);
            mainFrame.getInvoiceTable().setModel(hTable);

        }

    }

    private void deleteLine() {
        // int selectedHeaderRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedHeaderRow = mainFrame.getInvoiceTable().getSelectedRow();
        int selectedLineRow = mainFrame.getInvoiceItemsTable().getSelectedRow();
        if (selectedLineRow == -1 || selectedHeaderRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice line/InvoiceHeader to delete Line ", "delete erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            InvoiceHeader invoiceHeader = mainFrame.getHeadersArray().get(selectedHeaderRow);
            InvoiceLine invoiceLine = mainFrame.getHeadersArray().get(selectedHeaderRow).getLines().get(selectedLineRow);
            invoiceHeader.getLines().remove(selectedLineRow);
            double lineTotal = invoiceLine.getLineTotal();
            invoiceHeader.setInvoiceTotal(lineTotal * -1);
            String total = Double.toString(invoiceHeader.getInvoiceTotal());
            LineTable lTable = new LineTable();
            lTable.setItems(invoiceHeader.getLines());
            //MainFrame.invoiceItems.setModel(lTable);
            mainFrame.getInvoiceItemsTable().setModel(lTable);
            mainFrame.setInvoiceTotalLblText(total);

            HeaderTable hTable = new HeaderTable();
            hTable.setHeaders(mainFrame.getHeadersArray());
            // MainFrame.invoiceTable.setModel(hTable);
            mainFrame.getInvoiceTable().setModel(hTable);
        }

    }

    private void saveFile() {
        ArrayList<InvoiceHeader> invoiceHeadersArray = mainFrame.getHeadersArray();
        FileWriter headerFileWriter = null;
        FileWriter lineFileWriter = null;
        JOptionPane.showMessageDialog(null, "Select Invoice Header File", " Invoice Header ", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File headerFile = fileChooser.getSelectedFile();
            if (!(headerFile.getName().contains(".csv") || headerFile.getName().contains(".txt"))) {
                try {
                    throw new Exception("Wrong File Format");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Wrong Data Format ", "Date Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                headerFileWriter = new FileWriter(headerFile);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            String headers = "";
            String lines = "";
            for (InvoiceHeader header : invoiceHeadersArray) {
                headers += header.toString();
                headers += "\n";
                for (InvoiceLine line : header.getLines()) {
                    lines += line.toString();
                    lines += "\n";
                }
            }
            headers = headers.substring(0, headers.length() - 1);
            lines = lines.substring(0, lines.length() - 1);
            JOptionPane.showMessageDialog(null, "Select Invocie Lines file", " Invoice Items ", JOptionPane.INFORMATION_MESSAGE);
            int result = fileChooser.showSaveDialog(null);
            File lineFile = fileChooser.getSelectedFile();
            if (!(lineFile.getName().contains(".csv") || headerFile.getName().contains(".txt"))) {
                try {
                    throw new Exception("Wrong File Format");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Wrong Data Format ", "Date Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                lineFileWriter = new FileWriter(lineFile);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                headerFileWriter.write(headers);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                lineFileWriter.write(lines);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                headerFileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                lineFileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void loadFile() throws IOException, Exception {
        fileOperation = new FileOperation(mainFrame);
        JOptionPane.showMessageDialog(null, "Select Invoice Header File", " Invoice Header ", JOptionPane.INFORMATION_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            if (!headerFile.getName().contains(".csv")) {
                throw new Exception("Wrong File Format");
            }
            JOptionPane.showMessageDialog(null, "Select Invoice Lines File", " Invoice Lines ", JOptionPane.INFORMATION_MESSAGE);
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File lineFile = fileChooser.getSelectedFile();
                if (!lineFile.getName().contains(".csv")) {
                    throw new Exception("Wrong File Format");
                }

                headers = new ArrayList<>();
                invoiceItems = new ArrayList<>();
                ArrayList<String> headerLines = fileOperation.readFile(headerFile);
                ArrayList<String> itemLines = fileOperation.readFile(lineFile);
                //generate (invoice number,invoice date, cusomer name) from each HeaderLine
                for (String line : headerLines) {
                    String[] items = line.split(",");
                    Date date = new Date();
                    int invoiceNumber = Integer.parseInt(items[0]);
                    try {
                        date = MainFrame.formatter.parse(items[1]);
                    } catch (ParseException parserx) {
                        JOptionPane.showMessageDialog(null, "Wrong Data Format in File :" + headerFile, "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;

                    }
                    String customerName = items[2];
                    InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumber, (date), customerName);
                    headers.add(invoiceHeader);
                }
                //generate (invoice number ,custmoer Name,price,count) from each InvoiceLine
                for (String line : itemLines) {
                    String[] items = line.split(",");
                    int invoiceNumber = Integer.parseInt(items[0]);
                    String itemName = items[1];
                    double price = Double.parseDouble(items[2]);
                    int count = Integer.parseInt(items[3]);

                    if (price < 0 || count < 0 || invoiceNumber < 0) {
                        JOptionPane.showMessageDialog(null, "Wrong Data Format in File :" + lineFile, "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    InvoiceLine invoiceLine = null;
                    for (InvoiceHeader header : headers) {
                        if (invoiceNumber == header.getInvoiceNumber()) {
                            //invoiceLine = new InvoiceLine(invoiceNumber, itemName, price, count, header);
                            invoiceLine = new InvoiceLine(invoiceNumber, itemName, price, count, header);
                            header.setInvoiceTotal(invoiceLine.getLineTotal());
                            break;
                        }
                    }

                    invoiceItems.add(invoiceLine);

                }

                //////////
                for (InvoiceHeader header : headers) {
                    ArrayList<InvoiceLine> tmp = new ArrayList<>();
                    for (InvoiceLine invoiceline : invoiceItems) {
                        if (header.getInvoiceNumber() == invoiceline.getInvoiceNumber()) {
                            tmp.add(invoiceline);
                        }
                    }
                    header.setLines(tmp);
                }

                ////////
                HeaderTable hTable = new HeaderTable();
                mainFrame.setHeadersArray(headers);
                mainFrame.setLinesArray(invoiceItems);
                hTable.setHeaders(mainFrame.getHeadersArray());
                //  MainFrame.invoiceTable.setModel(hTable);
                mainFrame.getInvoiceTable().setModel(hTable);
                /////

                ///
            } else {
                JOptionPane.showMessageDialog(null, "Invoice Lines didn't selected", "Inovice Line Error", JOptionPane.ERROR_MESSAGE);

            }

        } else {
            JOptionPane.showMessageDialog(null, "Invoice Headers didn't selected", "Invoice Header Error", JOptionPane.ERROR_MESSAGE);

        }

    }

    private void addInvoice() {
        String invoiceNumber = hFrame.getInvoiceNumber();
        String customerName = hFrame.getCustomerNameTF();
        String date = hFrame.getInvoiceDateTF();
        Date dateParse = null;
        if (Integer.parseInt(invoiceNumber) < 0) {
            JOptionPane.showMessageDialog(null, "Wrong Data Invoice Number MUST BE GREATER THAN 0", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            dateParse = MainFrame.formatter.parse(date);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Date must be in format dd-mm-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("".equals(customerName)) {
            JOptionPane.showMessageDialog(null, "Enter Customer Name", "Customer Name Error", JOptionPane.ERROR_MESSAGE);

        } else {
            InvoiceHeader invoiceHeader = new InvoiceHeader(Integer.parseInt(invoiceNumber), dateParse, customerName);
            mainFrame.getHeadersArray().add(invoiceHeader);
            ArrayList<InvoiceLine> l = new ArrayList<>();
            invoiceHeader.setLines(l);
            HeaderTable hTable = new HeaderTable();
            hTable.setHeaders(mainFrame.getHeadersArray());
            //  MainFrame.invoiceTable.setModel(hTable);
            mainFrame.getInvoiceTable().setModel(hTable);
            hFrame.dispose();
        }
    }

    private void cancelInvoice() {
        hFrame.dispose();
    }

    private void cancelLine() {
        lFrame.dispose();
    }

    private void addLine() {
        //   int selectedRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedRow = mainFrame.getInvoiceTable().getSelectedRow();
        String itemName = lFrame.getNameTF();
        String itemPrice = lFrame.getPriceTF();
        String itemCount = lFrame.getCountTF();
        double price = Double.parseDouble(itemPrice);
        int count = Integer.parseInt(itemCount);
        if ("".equals(itemName) || "".equals(itemPrice) || "".equals(itemCount)) {
            JOptionPane.showMessageDialog(null, "Missing Data", "Customer Name Error", JOptionPane.ERROR_MESSAGE);
        } else if (price < 0 || count < 0) {
            JOptionPane.showMessageDialog(null, "Wrong Data price and count MUST BE GREATER THAN 0", " Wrong Data ", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            InvoiceLine invoiceline = new InvoiceLine(mainFrame.getHeadersArray().get(selectedRow).getInvoiceNumber(), itemName, price, count, mainFrame.getHeadersArray().get(selectedRow));
            mainFrame.getLinesArray().add(invoiceline);
            mainFrame.getHeadersArray().get(selectedRow).getLines().add(invoiceline);
            mainFrame.getHeadersArray().get(selectedRow).setInvoiceTotal(invoiceline.getLineTotal());
            HeaderTable hTable = new HeaderTable();
            LineTable lTable = new LineTable();
            lTable.setItems(mainFrame.getHeadersArray().get(selectedRow).getLines());
            // MainFrame.invoiceItems.setModel(lTable);
            mainFrame.getInvoiceItemsTable().setModel(lTable);
            hTable.setHeaders(mainFrame.getHeadersArray());
            //  MainFrame.invoiceTable.setModel(hTable);
            mainFrame.getInvoiceTable().setModel(hTable);

            lFrame.dispose();
        }

    }

}
