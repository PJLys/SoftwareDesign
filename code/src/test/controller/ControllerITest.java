package controller;

import databases.PersonDB;
import databases.TicketDB;
import factory.TicketFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerITest {
    public ControllerITest() {}
    TicketController controller;
    TicketDB tdb;
    PersonDB pdb;
    TicketFactory factory;
    @Before
    public void initialize(){
        tdb = TicketDB.getInstance();
        pdb = PersonDB.getInstance();
        factory = new TicketFactory();
        controller = new TicketController(tdb,pdb,factory);
    }
    @Test
    public void t_addTicket(){
        ExpenseType t = ExpenseType.TAXI;
        pdb.addPerson(new Person("Alex"));
        pdb.addPerson(new Person("Bart"));
        pdb.addPerson(new Person("Chad"));
        double total = 50.64;
        List<String> atts = new ArrayList<>();
        atts.add("Alex"); atts.add("Chad");
        Ticket ticket = controller.makeEvenSplitTicket(t,"Alex",total,atts);
        Assert.assertEquals("Should be type TAXI", ExpenseType.TAXI, ticket.getType());
    }
}
