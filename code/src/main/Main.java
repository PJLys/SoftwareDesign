import controller.*;
import databases.PersonDB;
import databases.TicketDB;
import factory.TicketFactory;
import person.Person;
import tickets.Ticket;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller tc = new TicketController(TicketDB.getInstance(), PersonDB.getInstance(), new TicketFactory());
        tc.addPerson("Eddie");
        tc.addPerson("Tina");
        tc.addPerson("Rita");
        tc.addPerson("George");

        Ticket est = tc.makeEvenSplitTicket();
        tc.addEvenSplitTicket(est);


    }
}
