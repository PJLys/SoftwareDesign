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
import java.util.LinkedList;


@RunWith(PowerMockRunner.class)

@PrepareForTest(TicketDB.class)

public class TicketDB_UTest {

    public TicketDB_UTest(){}
    @Before
    public void initialize(){}

    @Test
    public void t_addEntry() throws NoSuchFieldException, IllegalAccessException {
        Field field = TicketDB.class.getDeclaredField("db");
        field.setAccessible(true);

        TicketDB testDB = TicketDB.getInstance();
        Ticket mockTicket = Mockito.mock(Ticket.class);
        LinkedList<Ticket> mock_db =  (LinkedList<Ticket>) Mockito.mock(LinkedList.class);
        field.set(testDB, mock_db);

        testDB.addTicket(mockTicket);
        Mockito.verify(mock_db, Mockito.times(1)).add(mockTicket);
    }
}
