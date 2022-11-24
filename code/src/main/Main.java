import controller.*;
import databases.TicketDB;
import factory.TicketFactory;

public class Main {
    public int main() {
        TicketDB ticket = TicketDB.getInstance();
        Controller ticketController = new TicketController(ticket);
        TicketFactory factory = new TicketFactory();

        return 0;
    }
}
