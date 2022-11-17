package databases;

import tickets.Ticket;

import java.util.ArrayList;

public class TicketDB {
    private TicketDB instance = null;
    private ArrayList<Ticket> db;

    private TicketDB(){
        this.db = new ArrayList<>();
    }

    public TicketDB getInstance() {
        if (instance == null)
            this.instance = new TicketDB();
        return instance;
    }

    public int addTicket(Ticket t){
        if (!this.db.contains(t)){
            this.db.add(t);
            return 0;
        }
        return -1;
    }

    public int removeTicket(Ticket t){
        if (this.db.contains(t)){
            this.db.remove(t);
            return 0;
        }
        return -1;
    }
}
