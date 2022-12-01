package DBtest;

import databases.TicketDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tickets.Ticket;

import java.lang.reflect.Field;


@RunWith(PowerMockRunner.class)

@PrepareForTest(TicketDB.class)

public class TicketDB_UTest {
    public TicketDB_UTest(){}
    @Before
    public void initialize(){}

    @Test
    public void t_addEntry() throws NoSuchFieldException {
        Field field = TicketDB.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB testDB = TicketDB.getInstance();
        Ticket mockTicket = Mockito.mock(Ticket.class);

        testDB.addTicket(mockTicket);
        Mockito.verify(testDB, Mockito.times(1)).addTicket(mockTicket);
    }
}
