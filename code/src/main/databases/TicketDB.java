package databases;

import iterator.Aggregate;
import iterator.Iterator;
import tickets.Ticket;

import java.util.LinkedList;



/**
 * Ticket DB
 * - Singleton (private constructor & public getInstance())
 * - Aggregate for TicketIt
 */
public class TicketDB implements Aggregate, Database {

    private static TicketDB instance = null;

    /**
     * DB is a Linked List : easy addition & removal of Tickets
     */
    private final LinkedList<Ticket> db;

    /**
     * Private constructor (Singleton)
     */
    private TicketDB(){
        this.db = new LinkedList<>();
    }

    /**
     * Intializes or returns intialized db
     * @return db
     */
    @Override
    public TicketDB getInstance() {
        if (instance == null)
            instance = new TicketDB();
        return instance;
    }

    /**
     * @param t ticket to be added
     * @return 0 = succes; -1 = t is a duplicate
     */
    public int addTicket(Ticket t){
        if (!this.db.contains(t)){
            this.db.add(t);
            System.out.println("Added ticket");
            System.out.println(t.toString());
            return 0;
        }
        return -1;
    }

    /**
     * @param t ticket to be removed
     * @return 0 = succes; -1 = t isn't in the db
     */
    public int removeTicket(Ticket t){
        if (this.db.contains(t)){
            this.db.remove(t);
            return 0;
        }
        return -1;
    }

    /**
     * Return ticket in db equal to input
     * @param t ticket to find in db
     * @return ticket in db
     */
    public boolean find(Ticket t) {
        return this.db.stream().anyMatch(o -> o.equals(t));
    }

    @Override
    public Iterator createIt() {
        return new TicketIt();
    }

    /**
     * Used by iterator to get a ticket based on the index
     * @param index index of the iterator
     * @return Ticket at index
     */
    protected Ticket get(int index) {
        return this.db.get(index);
    }

    /**
     * Used by iterator to determine if ticket has next value
     * @return size of db
     */
    protected int size(){
        return this.db.size();
    }
}
