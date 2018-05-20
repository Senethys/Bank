package svimag6;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MoneyClass extends JFrame implements ActionListener {

  private static final long serialVersionUID = 1L;
  private Account           account;
  private JPanel            moneyPanel;
  private JTextField        moneyField;
  private JButton           withdrawButton   = new JButton("Withdraw");
  private JButton           depositButton    = new JButton("Deposit");
  protected double          withdrawAmount   = 0;
  protected double          depositAmount    = 0;
  DefaultTableModel         transactionModel;

  public MoneyClass(Account account, DefaultTableModel transactionModel) {
    this.transactionModel = transactionModel;
    this.account = account;
    initiateVariables();

  }

  public void initiateVariables() {
    setTitle("Money to transfer");
    setSize(300, 300);
    setLayout(new GridLayout(1, 2));
    moneyPanel = new JPanel(new GridLayout());
    moneyField = new JTextField();
    moneyPanel.add(moneyField);
    moneyField.setBorder(BorderFactory.createTitledBorder("Amount to Transfer"));
    withdrawButton.addActionListener(this);
    depositButton.addActionListener(this);
    add(moneyPanel);
    add(withdrawButton);
    add(depositButton);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String buttonText = event.getActionCommand();
    String amount;

    if (buttonText.equals("Withdraw")) {
      amount = moneyField.getText();
      withdrawAmount = Double.parseDouble(amount);
      account.withdraw(withdrawAmount);
      updateTransactionTable();
      this.setVisible(false);
      dispose();

    }
    if (buttonText.equals("Deposit")) {
      amount = moneyField.getText();
      depositAmount = Double.parseDouble(amount);
      account.deposit(depositAmount);
      updateTransactionTable();
      this.setVisible(false);
      dispose();

    }

  }

  public void updateTransactionTable() {
    ArrayList<Transaction> transactions = account.getAccountTransactions();
    for (Transaction t : transactions) {
      String[] transactionDetails = t.getTransacionDetails().split(" ");
      System.out.println(transactionDetails);
      transactionModel.addRow(
          new String[] { transactionDetails[0], transactionDetails[1], transactionDetails[2], transactionDetails[3] });
      System.out.println(transactionDetails);
    }

  }

}