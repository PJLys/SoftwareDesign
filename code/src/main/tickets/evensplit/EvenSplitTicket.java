package tickets.evensplit;

import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.util.ArrayList;

public class EvenSplitTicket extends Ticket {

    private final double ppp;
    private final ArrayList<Person> persons;

    public EvenSplitTicket(ExpenseType t, Person p, double pricePerPerson, ArrayList<Person> persons) {
        super(t, p);
        this.ppp = pricePerPerson;
        this.persons = persons;
    }

    public double getPpp() {
        return ppp;
    }
    public ArrayList<Person> getPersons(){
        return this.persons;
    }
}
