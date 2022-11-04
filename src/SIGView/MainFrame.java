package SIGView;

import SIGController.*;
import Model.*;

import javax.swing.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.logging.*;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        super("Sales Invoice Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        initComponents();
        try {
            controller.intiTables();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        final String title = "Invoice Table";
        javax.swing.border.Border blackline = javax.swing.BorderFactory.createTitledBorder(title);
        jPanel1.setBorder(blackline);
        invoiceTable = new javax.swing.JTable();
        invoiceTable.getSelectionModel().addListSelectionListener(tableSelectionController);
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        invoiceNumLbl = new javax.swing.JLabel();
        invoiceTotalLbl = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        final String titleLine = "Invoice Items";
        javax.swing.border.Border blackline1 = javax.swing.BorderFactory.createTitledBorder(title);
        jPanel2.setBorder(blackline1);
        invoiceItems = new javax.swing.JTable();
        createInvoieBtn = new javax.swing.JButton();
        createInvoieBtn.addActionListener(controller);
        deleteInvoiceBtn = new javax.swing.JButton();
        deleteInvoiceBtn.addActionListener(controller);
        createLineBtn = new javax.swing.JButton();
        createLineBtn.addActionListener(controller);
        deleteLineBtn = new javax.swing.JButton();
        deleteLineBtn.addActionListener(controller);
        customerNameLbl = new javax.swing.JTextField();
        customerNameLbl.addActionListener(controller);
        invoiceDateLbl = new javax.swing.JTextField();
        invoiceDateLbl.addActionListener(controller);
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadBtn = new javax.swing.JMenuItem();
        loadBtn.addActionListener(controller);
        saveBtn = new javax.swing.JMenuItem();
        saveBtn.addActionListener(controller);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        invoiceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(invoiceTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Inovice Number");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Customer Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Invoice Date");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Invoice Total");

        invoiceNumLbl.setText(".");

        invoiceTotalLbl.setText(".");

        invoiceItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(invoiceItems);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        createInvoieBtn.setText("Create New Invoice");

        deleteInvoiceBtn.setText("Delete Invoice");

        createLineBtn.setText("Create New Line");

        deleteLineBtn.setText("Delete Line");

        customerNameLbl.setText(".");
        customerNameLbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                customerNameLblKeyPressed(evt);
            }
        });

        invoiceDateLbl.setText(".");
        invoiceDateLbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                invoiceDateLblKeyPressed(evt);
            }
        });

        jMenu1.setText("File");

        loadBtn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadBtn.setText("Load File");
        jMenu1.add(loadBtn);

        saveBtn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveBtn.setText("Save File");
        jMenu1.add(saveBtn);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(invoiceTotalLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(invoiceDateLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(customerNameLbl, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(invoiceNumLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(createInvoieBtn)
                .addGap(91, 91, 91)
                .addComponent(deleteInvoiceBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(createLineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(deleteLineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(invoiceNumLbl))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(customerNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(invoiceDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(invoiceTotalLbl))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createInvoieBtn)
                    .addComponent(deleteInvoiceBtn)
                    .addComponent(createLineBtn)
                    .addComponent(deleteLineBtn))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void customerNameLblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_customerNameLblKeyPressed
        controller.invoiceCustomerNameLblKeyPressed(evt);
    }//GEN-LAST:event_customerNameLblKeyPressed

    private void invoiceDateLblKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_invoiceDateLblKeyPressed
        controller.invoiceDateLblKeyPressed(evt);
    }//GEN-LAST:event_invoiceDateLblKeyPressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createInvoieBtn;
    private javax.swing.JButton createLineBtn;
    private javax.swing.JTextField customerNameLbl;
    private javax.swing.JButton deleteInvoiceBtn;
    private javax.swing.JButton deleteLineBtn;
    private javax.swing.JTextField invoiceDateLbl;
    private javax.swing.JTable invoiceItems;
    private javax.swing.JLabel invoiceNumLbl;
    private javax.swing.JTable invoiceTable;
    private javax.swing.JLabel invoiceTotalLbl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem loadBtn;
    private javax.swing.JMenuItem saveBtn;
    // End of variables declaration//GEN-END:variables
    private Controller controller = new Controller(this);
    private TableSelectionController tableSelectionController = new TableSelectionController(this);
    private ArrayList<HeaderInvoice> headersArray;
    private ArrayList<invoiceSampla> linesArray;
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public Controller getController() {
        return controller;
    }

    public JTable getInvoiceItemsTable() {
        return invoiceItems;
    }

    public void setCustomerNameLblText(String customerNameLbl) {
        this.customerNameLbl.setText(customerNameLbl);
    }

    public JTable getInvoiceTable() {
        return invoiceTable;
    }

    public String getCustomerNameLblText() {
        return customerNameLbl.getText();
    }

    public String getInvoiceDateLblText() {
        return invoiceDateLbl.getText();
    }

    public void setInvoiceDateLblText(String invoiceDateLbl) {
        this.invoiceDateLbl.setText(
                (invoiceDateLbl));
    }

    public String getInvoiceTotalLblText() {
        return invoiceTotalLbl.getText();
    }

    public String getInvoiceNumLblText() {
        return invoiceNumLbl.getText();
    }

    public void setInvoiceNumLblText(String invoiceNumLbl) {
        this.invoiceNumLbl.setText(invoiceNumLbl);
    }

    public void setInvoiceTotalLblText(String invoiceTotalLbl) {
        this.invoiceTotalLbl.setText(invoiceTotalLbl);
    }

    public ArrayList<HeaderInvoice> getHeadersArray() {
        return headersArray;
    }

    public void setLinesArray(ArrayList<invoiceSampla> linesArray) {
        this.linesArray = linesArray;
    }

    public void setHeadersArray(ArrayList<HeaderInvoice> headersArray) {
        this.headersArray = headersArray;
    }

    public ArrayList<invoiceSampla> getLinesArray() {
        return linesArray;
    }

    public void setTableSelectionController(TableSelectionController sController) {
        this.tableSelectionController = sController;
    }

    public TableSelectionController getTableSelectionController() {
        return tableSelectionController;
    }

}
