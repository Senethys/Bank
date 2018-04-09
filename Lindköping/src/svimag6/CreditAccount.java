package svimag6;

/**
 * Denna klass tillhör en kund. SvaingsAccount innehar all data om ett konto.
 * 
 * @author Svitri Magnusson, svimag-6
 */

public class CreditAccount extends Account {

	private String type = "Kreditkonto";
	private double creditLimit = -5000.0;
	private double interestRate = 0.5;

	public CreditAccount() {
	}

	/**
	 * Efterssom det inte går att att lägga pengar i kredit konton så tas funktionen
	 * bort.
	 *
	 * @param double
	 *            amount
	 * @return void
	 */
	@Override
	public void deposit(double amount) {
		this.balance += amount;
		this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
		Transaction transaction = new Transaction(amount, this.balance);
		TransactionList.add(transaction);
		
	}

	/**
	 * Tar bort amount från kontot. Man kan inte ta bort mer än saldo. returnerar
	 * true om uttaget gick igenom.
	 * 
	 * @param double
	 *            amount
	 * @return boolean.
	 */
	@Override
	public boolean withdraw(double amount) {
		boolean result = false;
		if (creditLimit < (this.balance - Math.abs(amount))) {
			this.balance = this.balance - Math.abs(amount);
			result = true;
			this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
	    Transaction transaction = new Transaction(amount*-1, this.balance);
	    TransactionList.add(transaction);
		}

		else if (balance < creditLimit) {
			System.out.println("Credit card will overdrawn.");

		}
		return result;
	}

	/**
	 * Beräknar kontots ränta.
	 *
	 * @param void
	 * @return double
	 */
	public double calculateInterest() {
		this.interestRate = (this.balance > 0.0) ? 0.5 : 7.0;
		return (this.balance * this.interestRate / 100.0);

	}

	public String getAccountInfo() {
		return accountNumber + " " + balance + ' ' + type + ' ' + interestRate;
	}

	@Override
	public void changeAccountType(String newType) {
		this.type = newType;

	}

}
