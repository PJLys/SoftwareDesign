package controller;
import person.Person;
import tickets.Ticket;

import java.io.IOException;
import java.util.ArrayList;

public interface Controller {
    public int addEvenSplitTicket(Ticket t);
    public int addUnevenSplitTicket(Ticket t);
    public int addPerson(String name);
    public int removePerson(String name);
    public Ticket makeEvenSplitTicket() throws IOException;
    public Ticket makeUnevenSplitTicket() throws IOException;
    public Person getPerson(String name);

}
