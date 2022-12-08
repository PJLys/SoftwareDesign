package tickets.evensplit;

import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.util.ArrayList;

public class EvenSplitTicket extends Ticket {

    private final double ppp;
    private final ArrayList<Person> persons;

    /**
     * Constructor for the even split ticket
     * @param t Expense type
     * @param p payer
     * @param pricePerPerson Amount paid per person
     * @param persons List of attendants
     */
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

    @Override
    public String toString() {
        return "EvenSplitTicket{" +
                "Payer: " + super.getPayer() +
                "Price per person=" + ppp +
                ", Attendants=" + persons +
                '}';
    }
}
