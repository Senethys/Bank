package svimag6;
import java.util.ArrayList;



public class BankLogic {
  
  

  private ArrayList<Customer> CustomerList = new ArrayList();

  
  
  public BankLogic() {}


  //Returns customer object
  private Customer matchCustomer(String pNo) {

    String matchedpNo;
    Customer MatchedCustomerObject = null;

    for(int i = 0; i < CustomerList.size(); i++) {
      matchedpNo = CustomerList.get(i).getpNo(); {
        if(matchedpNo.equals(pNo)) {
          MatchedCustomerObject = CustomerList.get(i);
        }
      }
    }
    return MatchedCustomerObject;
  }

  
  //RReturnerar en ArrayList<String> som innehåller en presentation av bankens alla kunder på följande sätt: 
  //[Karl Karlsson 8505221898, Pelle Persson 6911258876, Lotta Larsson 7505121231]
  
  public ArrayList<String> getAllCustomers() {
   
    ArrayList<String> results = new ArrayList();
    String CustomerInfo;
    
    for(int i = 0; CustomerList.size() > i; i++) {
      CustomerInfo = CustomerList.get(i).getCustomerInfo();
      results.add(CustomerInfo);
    }
    return results;
  }
  
  
  

  public boolean createCustomer(String name, String surname, String pNo) {

    try {
      Customer NewCustomer = new Customer(name, surname, pNo);
      CustomerList.add(NewCustomer);
    } catch (IllegalStateException e) { 
      return false;
    }
    return true;
  }


  /**
   * Returnerar en ArrayList<String> som innehåller informationen om kunden inklusive dennes konton.
  Returnerar null om kunden inte fanns
  Första platsen i listan är reserverad för kundens namn och personnummer sedan följer informationen om kundens konton (kontonr saldo kontotyp räntesats)
  Exempel på hur det som returneras ska se ut:
  [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0, 1005 0.0 Sparkonto 1.0]

   */
  

  public ArrayList<String> getCustomer(String pNo){

    ArrayList<String> results = new ArrayList();
    String CustomerInfo;
    Customer MatchedCustomerObject;
    
    MatchedCustomerObject = matchCustomer(pNo);
    CustomerInfo = MatchedCustomerObject.getCustomerInfo();
    results.add(CustomerInfo);
    results.addAll(MatchedCustomerObject.getAllCustomerAccountInfo());
    return results;
  }


  /**
  Byter namn på vald kund, inparametern pNo anger vilken kund som ska få nytt namn.
  Returnerar true om namnet ändrades annars returnerar false (alltså om kunden inte fanns).

   */

  public boolean changeCustomerName(String name, String surname, String pNo) {
    Customer customer;
    boolean result = true;
    
    try {
      customer = matchCustomer(pNo);
      customer.changeName(name, surname);
    }
    catch(IllegalStateException e) {
      result = false;
    }
    return result;

  }

  /**
   * Tar bort kund ur banken, alla kundens eventuella konton tas också bort och resultatet returneras.
  Returnerar null om ingen kund togs bort
  Listan som returneras ska innehålla information om kund på första platsen i ArrayListen (förnamn efternamn personnummer) sedan följer de konton som togs bort (kontonr saldo kontotyp räntesats ränta). Ränta beräknas som saldo*räntesats/100 (ränta behöver enbart beräknas vid borttagning av konton då banken i denna version inte stödjer årsskiften).
  Det som returneras ska se ut som följer:
  [Lotta Larsson 7505121231, 1004 0.0 Sparkonto 1.0 0.0, 1005 700.0 Sparkonto 1.0 7.0]

   */
  
  
  //Ska returnera alla saker som tas bort.
  public ArrayList<String> deleteCustomer(String pNo){
    Customer customer;
    customer = matchCustomer(pNo);
    CustomerList.remove(customer);
    return null;

  }


  /**Skapar ett konto till kund med personnummer pNo
  Kontonummer ska vara unika för hela banken, inte bara för en enskild kund (se Big Java Late Objects på s. 400Preview the documentView in a new window för tips på lösning).
  Returnerar kontonumret som det skapade kontot fick
  Alternativt returneras –1 om inget konto skapades
   */
  public int createSavingsAccount(String pNo) {
    Customer MatchedCustomerObject;
    int accountNumber;
    
    try {
      MatchedCustomerObject = matchCustomer(pNo);
      accountNumber = MatchedCustomerObject.addAccount();
    }
    catch (IllegalStateException e) { 
    return -1;
    }
  return accountNumber;  
  }


  /**
   * 
  Returnerar en String som innehåller presentation av kontot med kontonnummer accountId som tillhör kunden pNo (kontonummer saldo, kontotyp räntesats).
  Returnerar null om konto inte finns eller om det inte tillhör kunden
   */
  public String getAccount(String pNo, int accountId) {
    String result;
    Customer customer;
    customer = matchCustomer(pNo);
    result = customer.getCustomerAccountInfo(accountId);
    
    return result;

  }
  /**Gör en insättning på konto med kontonnummer accountId som tillhör kunden pNo.
  Returnerar true om det gick bra annars false
   */

  public boolean deposit(String pNo, int accountId, double amount) {
    SavingsAccount account;
    Customer customer;
    customer = matchCustomer(pNo);
    account = customer.matchAccount(accountId);
    account.deposit(amount);
    return true;

  }

  /**Gör ett uttag på konto med kontonnummer accountId som tillhör kunden pNo.
  Uttaget genomförs endast om saldot täcker uttaget (saldot får inte bli mindre än 0)
  Returnerar true om det gick bra annars false
   */
  public boolean withdraw(String pNo, int accountId, double amount) {
    SavingsAccount account;
    Customer customer;
    customer = matchCustomer(pNo);
    account = customer.matchAccount(accountId);
    account.withdraw(amount);
    return true;

  }


  /**Avslutar ett konto med kontonnummer accountId som tillhör kunden pNo. När man avslutar ett konto skall räntan beräknas som saldo*ränta/100.
  OBS! Enda gången ränta läggs på är när kontot tas bort eftersom årsskiften inte hanteras i denna version av systemet.
  Presentation av kontot ska returneras inklusive räntan man får på pengarna (kontonummer saldo, kontotyp räntesats, ränta)
  Returnerar null om inget konto togs bort
   */

  public boolean closeAccount(String pNr, int accountId) {
    Customer customer;
    boolean result;
    customer = matchCustomer(pNr);
    result = customer.deleteAccount(accountId);
    return result;

  }


  public static void main(String[] args) {

    String welcomeMessage = "---Welcome to SeBanking Co.---\n";

    System.out.println(welcomeMessage);

    BankLogic BankApp = new BankLogic();
    BankApp.createCustomer("Svitri", "Magnusson", "9303207931");
    Customer testCustomer = new Customer("TEstname", "surname", "9303207931");
    String test = testCustomer.getCustomerInfo();



  }
}
