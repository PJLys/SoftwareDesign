package databases;

import iterator.Iterator;

public class TicketIt implements Iterator {
    private int index;
    public TicketIt() {
        this.index = -1;
    }

    @Override
    public boolean hasNext() {
        return this.index < (TicketDB.getInstance().size()-1);
    }

    @Override
    public Object next() {
        return TicketDB.getInstance().get(++index);
    }

    @Override
    public int remove() {
        TicketDB db = TicketDB.getInstance();
        return db.removeTicket(db.get(this.index));
    }
}
