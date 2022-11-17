package tickets;

import person.Person;

public class EvenSplitTicket extends Ticket{

    private final double ppp;
    private final Person[] persons;

    public EvenSplitTicket(ExpenseType t, Person p, double pricePerPerson, Person[] persons) {
        super(t, p);
        this.ppp = pricePerPerson;
        this.persons = persons;
    }
}
