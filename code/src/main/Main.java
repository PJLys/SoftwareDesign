import controller.*;
import databases.PersonDB;
import databases.TicketDB;
import factory.TicketFactory;

public class Main {
    public static void main(String[] args) {
        Controller tc = new TicketController(TicketDB.getInstance(), PersonDB.getInstance(), new TicketFactory());

    }
}
