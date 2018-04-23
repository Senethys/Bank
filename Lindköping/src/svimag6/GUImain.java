package svimag6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.awt.*;

//YOU NEED TO ADD A FIELD in the GUI FOR NAME, LASTNAME AND PNR

public class GUImain extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private BankLogic         logic;
  private JList<Object>     customerList;
  private JTable            accountTable;
  private JTable            transactionTable;
  private JTextField        nameField;
  private JTextField        lastnameField;
  private JTextField        pNrField;

  private DefaultTableModel model;
  private JButton addButton                  = new JButton("Add Customer");
  private JButton showButton                 = new JButton("Show");
  private JButton clearButton                = new JButton("Rensa");
  private JButton createSavingsAccountButton = new JButton("Create Savings Account");
  private JButton createCreditAccountButton  = new JButton("Create Credit Account");
  private JButton deleteAccountButton        = new JButton("Delete Account");
  private JButton withdrawButton             = new JButton("Withdraw");
  private JButton depositButton              = new JButton("Deposit");

  String[] accountColumns = { "ID", "Account Type", "Balance", "Interest" };

  String data[][] = { { "101", "Amit", "670000" }, { "102", "Jai", "780000" }, { "101", "Sachin", "700000" } };

  public static void main(String[] args) {
    GUImain frame = new GUImain();
    frame.setVisible(true);
  }

  public GUImain() {
    initiateInstanceVariables();
    buildFrame();
  }

  private void initiateInstanceVariables() {
    logic = new BankLogic();
    
    model = new DefaultTableModel();
    accountTable = new JTable(model);
 
    transactionTable = new JTable();
    nameField = new JTextField();
    lastnameField = new JTextField();
    pNrField = new JTextField();
    customerList = new JList<Object>();

    nameField.setBorder(BorderFactory.createTitledBorder("Name"));
    lastnameField.setBorder(BorderFactory.createTitledBorder("Lastname"));
    pNrField.setBorder(BorderFactory.createTitledBorder("pNr"));

  }

  private void buildFrame() {
    setTitle("Bank");
    setSize(300, 250);
    setLayout(new GridLayout(1, 2));

    JPanel bankpanel = new JPanel(new GridLayout(5, 1));
    bankpanel.add(nameField);
    bankpanel.add(lastnameField);
    bankpanel.add(pNrField);
    bankpanel.add(addButton);
    bankpanel.add(showButton);
    bankpanel.add(clearButton);
    bankpanel.add(createSavingsAccountButton);
    bankpanel.add(createCreditAccountButton);

    createSavingsAccountButton.setVisible(false);
    createCreditAccountButton.setVisible(false);

    addButton.addActionListener(this);
    showButton.addActionListener(this);
    clearButton.addActionListener(this);
    createSavingsAccountButton.addActionListener(this);
    createCreditAccountButton.addActionListener(this);
    deleteAccountButton.addActionListener(this);

    // ACCOUNT TABLE
    accountTable.setCellSelectionEnabled(true);
    ListSelectionModel select = accountTable.getSelectionModel();
    select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    // ACCOUNT TABLE

    add(bankpanel);
    add(customerList);
    add(new JScrollPane(accountTable), BorderLayout.CENTER);
    add(transactionTable);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  // UI LOGIC. This activated the functions below.
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    if (buttonText.equals("Add Customer")) {
      addCustomer();
      showAccountButtons();
    }
    if (buttonText.equals("Show")) {
      showSelected();
    }
    if (buttonText.equals("Clear")) {
      clear();
    }
    if (buttonText.equals("Create Savings Account")) {
      createSavingsAccount();
    }
    if (buttonText.equals("Create Credit Account")) {
      createCreditAccount();
    }
  }

  private void addCustomer() {
    logic.createCustomer(nameField.getText(), lastnameField.getText(), pNrField.getText());
    customerList.setListData(logic.getAllCustomers().toArray());
    clear();
  }

  private void createCreditAccount() {
    logic.createCreditAccount(pNrField.getText());
    model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});

  }

  private void createSavingsAccount() {
    logic.createSavingsAccount(pNrField.getText());
    accountTable.setListData(logic.getAllCustomers().toArray());
    clear();
  }

  private void showSelected() {
    int position = customerList.getSelectedIndex();
    if (position > -1) {
      nameField.setText(logic.getNameForPersonAt(position));
      lastnameField.setText(logic.getLastNameForPersonAt(position));
      pNrField.setText(logic.getpNrAt(position));
    } else {
      JOptionPane.showMessageDialog(null, "Du måste markera en person i listan!");
    }
  }

  private void showAccountButtons() {
    createSavingsAccountButton.setVisible(true);
    createCreditAccountButton.setVisible(true);
  }

  private void clear() {
    nameField.setText("");
    lastnameField.setText("");
    pNrField.setText("");
  }
}