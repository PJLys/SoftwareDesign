package database;

import databases.TicketDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tickets.Ticket;

import java.lang.reflect.Field;
import java.util.LinkedList;


@RunWith(PowerMockRunner.class)

@PrepareForTest(TicketDB.class)

public class TicketDB_UTest {
    private Field field;
    private TicketDB testDB;
    private LinkedList<Ticket> mock_db;
    private Ticket mockTicket;
    public TicketDB_UTest(){}
    @Before
    public void initialize() throws NoSuchFieldException {
        this.field = TicketDB.class.getDeclaredField("db");
        this.field.setAccessible(true);

        this.testDB = TicketDB.getInstance();

        this.mock_db = (LinkedList<Ticket>) Mockito.mock(LinkedList.class);

        this.mockTicket = Mockito.mock(Ticket.class);
    }

    @Test
    public void t_addTicket() throws IllegalAccessException {
        this.field.set(this.testDB, this.mock_db);
        this.testDB.addTicket(this.mockTicket);
        Mockito.verify(this.mock_db, Mockito.times(1)).add(this.mockTicket);
    }
}
