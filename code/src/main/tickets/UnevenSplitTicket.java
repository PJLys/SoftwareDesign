package tickets;

import person.Person;

class TicketEntry {
    Person p;
    double val;
    public TicketEntry(Person p, double val){
        this.p = p;
        this.val = val;
    }
}

public class UnevenSplitTicket extends Ticket{
    private final TicketEntry[] entries;
    public UnevenSplitTicket(ExpenseType t, Person p, TicketEntry[] entries) {
        super(t, p);
        this.entries = entries;
    }
}
