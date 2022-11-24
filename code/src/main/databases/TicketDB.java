package databases;

import tickets.Ticket;
import java.util.LinkedList;


/**
 * Ticket DB
 * - Singleton (private constructor & public getInstance())
 */
public class TicketDB {

    private static TicketDB instance = null;

    /**
     * DB is a Linked List : easy addition & removal of Tickets
     */
    private LinkedList<Ticket> db;

    private TicketDB(){
        this.db = new LinkedList<>();
    }

    public static TicketDB getInstance() {
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

    public boolean find(Ticket t){
        return this.db.stream().findAny().equals(t);
    }
}
