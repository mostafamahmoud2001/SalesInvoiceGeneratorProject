package SIGController;

import Model.*;
import SIGView.*;

import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements ActionListener {

    private ArrayList<HeaderInvoice> hLine;
    private HeaderFrame invoiceF;
    private LineFrame lineF;
    private FileOP fileOp;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ArrayList<invoiceSampla> invLines;
    private final MainFrame main;

    public Controller(MainFrame mainFrame) {
        this.main = mainFrame;
    }

    public void invoiceDateLblKeyPressed(java.awt.event.KeyEvent evt) {
        int Row = main.getInvoiceTable().getSelectedRow();
        if (evt.getKeyCode() == 10 && Row != -1) {
            javax.swing.JTextField x = (javax.swing.JTextField) evt.getComponent();
            try {
                Date parse = dateFormat.parse(x.getText());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Date Not In Formate dd-MM-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            HeaderInvoice invoiceHeader = main.getHeadersArray().get(Row);
            if (invoiceHeader.getInvoiceDate() != x.getText()) {
                invoiceHeader.setInvoiceDate(x.getText());
                TableHeader hTable = new TableHeader();
                hTable.setHeaders(main.getHeadersArray());
                main.getInvoiceTable().setModel(hTable);

            }
        }

    }

    public void invoiceCustomerNameLblKeyPressed(java.awt.event.KeyEvent evt) {
        int Row = main.getInvoiceTable().getSelectedRow();
        if (evt.getKeyCode() == 10 && Row != -1) {
            javax.swing.JTextField x = (javax.swing.JTextField) evt.getComponent();
            HeaderInvoice invoiceHeader = main.getHeadersArray().get(Row);
            if (invoiceHeader.getCustomerName() != x.getText()) {
                invoiceHeader.setCustomerName(x.getText());
                TableHeader hTable = new TableHeader();
                hTable.setHeaders(main.getHeadersArray());
                main.getInvoiceTable().setModel(hTable);
            }
        }
    }

//
    public void intiTables() throws IOException {
        fileOp = new FileOP(main);
        hLine = new ArrayList<>();
        invLines = new ArrayList<>();
        final File lineFile = new File("InvoiceLine.csv");
        final File headerFile = new File("InvoiceHeader.csv");
        System.out.println(headerFile.canRead());
        if (!headerFile.canRead() || !lineFile.canRead()) {
            JOptionPane.showMessageDialog(null, "File Not Found ", "Date Error", JOptionPane.ERROR_MESSAGE);
            throw new FileNotFoundException();

        }
        ArrayList<String> itemLines = fileOp.readFile(lineFile);

        ArrayList<String> headerLines = fileOp.readFile(headerFile);
        for (String line : headerLines) {
            Date date = new Date();

            String[] items = line.split(",");
            int invoiceNumber = Integer.parseInt(items[0]);
            try {
                date = dateFormat.parse(items[1]);
            } catch (ParseException parserx) {
                JOptionPane.showMessageDialog(null, "Date must be in format dd-MM-yyyy", "Date Error", JOptionPane.ERROR_MESSAGE);
            }
            String customerName = items[2];
            HeaderInvoice invoiceHeader = new HeaderInvoice(invoiceNumber, date, customerName);
            hLine.add(invoiceHeader);
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

            invoiceSampla invoiceLine = null;
            for (HeaderInvoice header : hLine) {
                if (invoiceNumber == header.getInvoiceNumber()) {
                    invoiceLine = new invoiceSampla(invoiceNumber, customerName, price, count, header);
                    header.setInvoiceTotal(invoiceLine.getLineTot());
                    break;
                }
            }

            invLines.add(invoiceLine);
        }
        for (HeaderInvoice header : hLine) {
            ArrayList<invoiceSampla> tmp = new ArrayList<>();
            for (invoiceSampla invoiceline : invLines) {
                if (header.getInvoiceNumber() == invoiceline.getInvNumber()) {
                    tmp.add(invoiceline);
                }
            }
            header.setLines(tmp);
        }

        main.setHeadersArray(hLine);
        main.setLinesArray(invLines);
        TableHeader hTable = new TableHeader();
        hTable.setHeaders(main.getHeadersArray());
        main.getInvoiceTable().setModel(hTable);
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Wrong File Format ", "Date Error", JOptionPane.ERROR_MESSAGE);
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
        for (HeaderInvoice h : hLine) {
            num = Math.max(num, h.getInvoiceNumber());
            totalIDs += h.getInvoiceNumber();
        }
        int totalSum = (num * (num + 1)) / 2;
        if (totalSum != totalIDs) {
            num = totalSum - totalIDs;
        } else {
            num++;
        }
        invoiceF = new HeaderFrame(main);
        invoiceF.setInvoiceNumber(Integer.toString(num));
    }
    private void createLine() {
        int selRow = main.getInvoiceTable().getSelectedRow();
        if (selRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice Header to add items on it ", "Insertion Failure", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Enter Line  details ", " ", JOptionPane.INFORMATION_MESSAGE);
            lineF = new LineFrame(main);
        }
    }
    private void deleteInvoice() {
        int selRow = main.getInvoiceTable().getSelectedRow();
        if (selRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice Header to delete it ", "delete erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            ArrayList<invoiceSampla> emptyLines = new ArrayList<>();
            main.getHeadersArray().get(selRow).setLines(emptyLines);
            TableSamble lTable = new TableSamble();
            lTable.setItems(main.getHeadersArray().get(selRow).getLines());
            main.getInvoiceItemsTable().setModel(lTable);
            main.setCustomerNameLblText(",");
            main.setInvoiceDateLblText(",");
            main.setInvoiceNumLblText(",");
            main.setInvoiceTotalLblText("0");
            main.getHeadersArray().remove(selRow);
            TableHeader hTable = new TableHeader();
            hTable.setHeaders(main.getHeadersArray());
            main.getInvoiceTable().setModel(hTable);
        }
    }
    private void deleteLine() {
        int SelHedRow = main.getInvoiceTable().getSelectedRow();
        int selLineRow = main.getInvoiceItemsTable().getSelectedRow();
        if (selLineRow == -1 || SelHedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select Invoice line/InvoiceHeader to delete Line ", "delete erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            HeaderInvoice invoiceHeader = main.getHeadersArray().get(SelHedRow);
            invoiceSampla invoiceLine = main.getHeadersArray().get(SelHedRow).getLines().get(selLineRow);
            invoiceHeader.getLines().remove(selLineRow);
            double lineTotal = invoiceLine.getLineTot();
            invoiceHeader.setInvoiceTotal(lineTotal * -1);
            String total = Double.toString(invoiceHeader.getInvoiceTotal());
            TableSamble lTable = new TableSamble();
            lTable.setItems(invoiceHeader.getLines());
            main.getInvoiceItemsTable().setModel(lTable);
            main.setInvoiceTotalLblText(total);
            TableHeader hTable = new TableHeader();
            hTable.setHeaders(main.getHeadersArray());
            main.getInvoiceTable().setModel(hTable);
        }

    }

    private void saveFile() {
        ArrayList<HeaderInvoice> invoiceHeadersArray = main.getHeadersArray();
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
            for (HeaderInvoice header : invoiceHeadersArray) {
                headers += header.toString();
                headers += "\n";
                for (invoiceSampla line : header.getLines()) {
                    lines += line.toString();
                    lines += "\n";
                }
            }
            headers = headers.substring(0, headers.length() - 1);
            lines = lines.substring(0, lines.length() - 1);
            JOptionPane.showMessageDialog(null, "Select Invocie Lines file", " Invoice Items ", JOptionPane.INFORMATION_MESSAGE);
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
        fileOp = new FileOP(main);
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
                hLine = new ArrayList<>();
                invLines = new ArrayList<>();
                ArrayList<String> headerLines = fileOp.readFile(headerFile);
                ArrayList<String> itemLines = fileOp.readFile(lineFile);
                for (String line : headerLines) {
                    String[] items = line.split(",");
                    Date date;
                    date = new Date();
                    int invoiceNumber = Integer.parseInt(items[0]);
                    try {
                        date = MainFrame.formatter.parse(items[1]);
                    } catch (ParseException parserx) {
                        JOptionPane.showMessageDialog(null, "Wrong Data Format in File :" + headerFile, "Date Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    String customerName = items[2];
                    HeaderInvoice invoiceHeader = new HeaderInvoice(invoiceNumber, (date), customerName);
                    hLine.add(invoiceHeader);
                }
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
                    invoiceSampla invoiceLine = null;
                    for (HeaderInvoice header : hLine) {
                        if (invoiceNumber == header.getInvoiceNumber()) {
                            invoiceLine = new invoiceSampla(invoiceNumber, itemName, price, count, header);
                            header.setInvoiceTotal(invoiceLine.getLineTot());
                            break;
                        }
                    }
                    invLines.add(invoiceLine);
                }
                for (HeaderInvoice header : hLine) {
                    ArrayList<invoiceSampla> tmp = new ArrayList<>();
                    for (invoiceSampla invoiceline : invLines) {
                        if (header.getInvoiceNumber() == invoiceline.getInvNumber()) {
                            tmp.add(invoiceline);
                        }
                    }
                    header.setLines(tmp);
                }
                TableHeader hTable = new TableHeader();
                main.setHeadersArray(hLine);
                main.setLinesArray(invLines);
                hTable.setHeaders(main.getHeadersArray());
                main.getInvoiceTable().setModel(hTable);
            } else {
                JOptionPane.showMessageDialog(null, "Invoice Lines didn't selected", "Inovice Line Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invoice Headers didn't selected", "Invoice Header Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void addInvoice() {
        String invoiceNumber = invoiceF.getInvoiceNumber();
        String customerName = invoiceF.getCustomerNameTF();
        String date = invoiceF.getInvoiceDateTF();
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
            HeaderInvoice invoiceHeader = new HeaderInvoice(Integer.parseInt(invoiceNumber), dateParse, customerName);
            main.getHeadersArray().add(invoiceHeader);
            ArrayList<invoiceSampla> l = new ArrayList<>();
            invoiceHeader.setLines(l);
            TableHeader hTable = new TableHeader();
            hTable.setHeaders(main.getHeadersArray());
            main.getInvoiceTable().setModel(hTable);
            invoiceF.dispose();
        }
    }

    private void cancelInvoice() {
        invoiceF.dispose();
    }

    private void cancelLine() {
        lineF.dispose();
    }

    private void addLine() {
        //   int selectedRow = MainFrame.invoiceTable.getSelectedRow();
        int selectedRow = main.getInvoiceTable().getSelectedRow();
        String itemName = LineFrame.getNameTF();
        String itemPrice = LineFrame.getPriceTF();
        String itemCount = LineFrame.getCountTF();
        double price = Double.parseDouble(itemPrice);
        int count = Integer.parseInt(itemCount);
        if ("".equals(itemName) || "".equals(itemPrice) || "".equals(itemCount)) {
            JOptionPane.showMessageDialog(null, "Missing Data", "Customer Name Error", JOptionPane.ERROR_MESSAGE);
        } else if (price < 0 || count < 0) {
            JOptionPane.showMessageDialog(null, "Wrong Data price and count MUST BE GREATER THAN 0", " Wrong Data ", JOptionPane.ERROR_MESSAGE);
        } else {
            invoiceSampla invoiceline = new invoiceSampla(main.getHeadersArray().get(selectedRow).getInvoiceNumber(), itemName, price, count, main.getHeadersArray().get(selectedRow));
            main.getLinesArray().add(invoiceline);
            main.getHeadersArray().get(selectedRow).getLines().add(invoiceline);
            main.getHeadersArray().get(selectedRow).setInvoiceTotal(invoiceline.getLineTot());
            TableHeader hTable = new TableHeader();
            TableSamble lTable = new TableSamble();
            lTable.setItems(main.getHeadersArray().get(selectedRow).getLines());
            main.getInvoiceItemsTable().setModel(lTable);
            hTable.setHeaders(main.getHeadersArray());
            main.getInvoiceTable().setModel(hTable);
            lineF.dispose();
        }

    }

}
