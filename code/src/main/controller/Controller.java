package controller;
import tickets.Ticket;

public interface Controller {
    public int addEvenSplitTicket(Ticket t);
    public int addUnevenSplitTicket(Ticket t);
    public int addPerson(String name);
    public int removePerson(String name);
}
