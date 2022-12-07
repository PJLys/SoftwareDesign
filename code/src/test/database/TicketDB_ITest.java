package database;

import databases.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import person.Person;
import tickets.ExpenseType;
import tickets.Ticket;
import tickets.evensplit.EvenSplitTicket;
import tickets.unevensplit.UnevenEntry;
import tickets.unevensplit.UnevenSplitTicket;

import java.util.ArrayList;

public class TicketDB_ITest {
    public TicketDB_ITest() {}
    TicketDB tdb;
    Ticket t, t2, t3, t4;
    Person alex, bart, chad;
    ArrayList<Person> attendants;
    ArrayList<UnevenEntry> entries;

    @Before
    public void initialize(){
        tdb = TicketDB.getInstance();
        alex = new Person("Alex");
        bart = new Person("Bart");
        chad = new Person("Chad");

        attendants = new ArrayList<>();
        attendants.add(bart);
        attendants.add(chad);
        t = new EvenSplitTicket(ExpenseType.TAXI, alex, 50, attendants);
        t2 = new EvenSplitTicket(ExpenseType.TAXI, alex, 50, attendants);

        entries = new ArrayList<>();
        entries.add(new UnevenEntry(bart, 50));
        entries.add(new UnevenEntry(chad, 20));

        t3 = new UnevenSplitTicket(ExpenseType.TAXI, alex, entries);
    }

    @Test
    public void t_addRemoveEST() {
        Assert.assertEquals("Removing non added EST, should return -1", -1, tdb.removeTicket(t));
        Assert.assertEquals("Adding EST, should return 0",0,tdb.addTicket(t));
        Assert.assertEquals("Adding duplicate, should return -1",-1,tdb.addTicket(t));
        Assert.assertEquals("Removing EST, should return 0", 0, tdb.removeTicket(t));
    }

    @Test
    public void t_findEST() {
        Assert.assertFalse("Shouldn't be able to find unadded EST", tdb.find(t));
        tdb.addTicket(t);
        Assert.assertTrue("Should be able to find added EST", tdb.find(t));
    }

    @Test
    public void t_similarEST() {
        tdb.addTicket(t);
        Assert.assertEquals("Should be able to add EQUAL ticket (not duplicate)", 0, tdb.addTicket(t2));
    }

    @Test
    public void t_addRemoveUST() {
        Assert.assertEquals("Removing non added UST, should return -1", -1, tdb.removeTicket(t3));
        Assert.assertEquals("Adding UST, should return 0",0,tdb.addTicket(t3));
        Assert.assertEquals("Adding duplicate, should return -1",-1,tdb.addTicket(t3));
        Assert.assertEquals("Removing UST, should return 0", 0, tdb.removeTicket(t3));
    }

    @Test
    public void t_findUST() {
        Assert.assertFalse("Shouldn't be able to find unadded EST", tdb.find(t3));
        tdb.addTicket(t3);
        Assert.assertTrue("Should be able to find added EST", tdb.find(t3));
    }

    @Test
    public void t_similarUST() {
        tdb.addTicket(t3);
        Assert.assertEquals("Should be able to add EQUAL ticket (not duplicate)", 0, tdb.addTicket(t4));
    }
}
