package factory;

import person.Person;
import tickets.*;
import tickets.evensplit.EvenSplitTicket;
import tickets.unevensplit.UnevenEntry;
import tickets.unevensplit.UnevenSplitTicket;

import java.util.ArrayList;

public class TicketFactory {
    public TicketFactory(){}

    /**
     * Returns a new EST
     * @param type ExpenseType (Cab, Train, Theatre)
     * @param payer person who pays...
     * @param total Total amount paid
     * @param attendants People who are paid for
     * @return EST
     */
    public Ticket createEvenSplitTicket(ExpenseType type, Person payer, double total, ArrayList<Person> attendants) {
        return new EvenSplitTicket(type, payer, total/(1+attendants.size()), attendants);
    }

    /**
     * Returns a new UST
     * @param type ExpenseType (Cab, Train, Theatre)
     * @param payer person who pays...
     * @param entries Links a debt to the attendants
     * @return UST
     */
    public Ticket createUnevenSplitTicket(ExpenseType type, Person payer, ArrayList<UnevenEntry> entries){
        return new UnevenSplitTicket(type, payer, entries);
    }
}
