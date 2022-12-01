package databases;

import iterator.Iterator;
import tickets.Ticket;


public class TicketIt implements Iterator {
    private int index;
    public TicketIt() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return TicketDB.getInstance().get(index++);
    }

    @Override
    public int remove() {
        TicketDB db = TicketDB.getInstance();
        return db.removeTicket(db.get(this.index));
    }
}
