package tickets.unevensplit;

import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.util.ArrayList;

/**
 * Stores variable cost events
 */
public class UnevenSplitTicket extends Ticket {
    private final ArrayList<UnevenEntry> entries;

    /**
     * Constructer of a UST
     * @param t Expense type
     * @param p payer
     * @param entries List of people and their respective debt
     */
    public UnevenSplitTicket(ExpenseType t, Person p, ArrayList<UnevenEntry> entries) {
        super(t, p);
        this.entries = entries;
    }
    public ArrayList<UnevenEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "UnevenSplitTicket{" +
                "Payer: " + super.getPayer() +
                "\tEntries: " + entries +
                '}';
    }
}
