package debts;

import person.Person;

/**
 * Data structure which contains the debts of all the people
 */
public class Debt {
    public Debt(Person from, Person to, double amount){
        this.amount = amount;
        this.from = from;
        this.to = to;
    }
    public Person from, to;
    public double amount;
}
