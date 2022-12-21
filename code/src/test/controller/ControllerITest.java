package controller;

import databases.PersonDB;
import databases.TicketDB;
import debts.Transaction;
import factory.TicketFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;
import tickets.evensplit.EvenSplitTicket;

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
        Ticket ticket2 = controller.makeEvenSplitTicket(t,"Alex",total,atts);
        Assert.assertEquals("Should be type TAXI", ExpenseType.TAXI, ticket2.getType());
    }

    @Test
    public void t_addPerson(){
        controller.addPerson("Alex");
        int code = controller.addPerson("Alex");
        Assert.assertEquals("Should return error code from DB", -1, code);

        Person getAlex = controller.getPerson("Alex");
        Assert.assertNotNull("Alex is added, so should be not null", getAlex);

        Person getBart = controller.getPerson("Bart");
        Assert.assertNull("Bart isn't added, so should return null", getBart);
    }

    @Test
    public void t_calcDebt(){
        pdb.addPerson(new Person("Alex"));
        pdb.addPerson(new Person("Peter"));
        ArrayList<Person> atts = new ArrayList<>();
        atts.add(controller.getPerson("Alex"));

        ArrayList<Transaction> ts = controller.calcDebt();
        Assert.assertTrue("Should return empty Vector", ts.isEmpty());

        tdb.addTicket(new EvenSplitTicket(ExpenseType.TAXI, controller.getPerson("Peter"), 50, atts));
        ts = controller.calcDebt();
        Assert.assertEquals("Number of transactions should be 1", 1, ts.size());

        tdb.addTicket(new EvenSplitTicket(ExpenseType.TAXI, controller.getPerson("Peter"), 20, atts));
        ts = controller.calcDebt();
        Assert.assertEquals("Number of transactions should be 1", 1, ts.size());

        ArrayList<Person> atts2 = new ArrayList<>();
        atts2.add(controller.getPerson("Peter"));

        tdb.addTicket(new EvenSplitTicket(ExpenseType.TAXI, controller.getPerson("Alex"), 70, atts2));
        ts = controller.calcDebt();

        Assert.assertEquals("Number of transactions should be 0", 0, ts.size());
    }
}
