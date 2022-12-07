package controller;

import databases.PersonDB;
import databases.TicketDB;
import factory.TicketFactory;
import org.junit.Test;
import org.junit.Before;

import java.beans.PropertyChangeListener;

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
    public void t_getAddedTicket(){
    }
}
