package debts;

import person.Person;

/**
 * Data structure which contains the debts of all the people
 */
public class Transaction {
    public Transaction(Person from, Person to, double amount){
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
    public Person from, to;
    public double amount;

    /**
     * Used in order to represent the transactions
     * @return Transaction in string format
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "from " + from +
                ", to " + to +
                ", amount: €" + amount +
                '}';
    }
}
