import controller.*;
import databases.PersonDB;
import databases.TicketDB;
import factory.TicketFactory;
import view.ViewFrame;

public class Main {
    public static void main(String[] args) {
        ViewFrame viewFrame = new ViewFrame();
        viewFrame.initialize(new TicketController(TicketDB.getInstance(),  PersonDB.getInstance(), new TicketFactory()));
        TicketDB.getInstance().addPropertyChangeListener("TicketAdded", viewFrame);
        PersonDB.getInstance().addPropertyChangeListener("PersonAdded", viewFrame);
        PersonDB.getInstance().addPropertyChangeListener("PersonAlreadyExists", viewFrame);
        PersonDB.getInstance().addPropertyChangeListener("PersonRemoved", viewFrame);
        PersonDB.getInstance().addPropertyChangeListener("PersonDoesNotExist", viewFrame);
    }
}
