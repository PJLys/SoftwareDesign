package tickets.unevensplit;

import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.util.ArrayList;

public class UnevenSplitTicket extends Ticket {
    private final ArrayList<UnevenEntry> entries;
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
                "entries: " + entries +
                '}';
    }
}
