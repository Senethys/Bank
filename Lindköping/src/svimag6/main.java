package svimag6;

import java.util.ArrayList;

public class main {

  public static void main(String[] args) {
    BankLogic bank = new BankLogic();
    boolean r = bank.createCustomer("Svitri", "Magnusson", "9303201337");
    ArrayList<String> result = bank.getAllCustomers();
    System.out.println("  " + result);
  }
}
