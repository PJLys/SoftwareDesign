package controller;

import databases.*;
import person.Person;
import tickets.EvenSplitTicket;
import tickets.Ticket;
import tickets.UnevenSplitTicket;

import java.util.Optional;

public class TicketController implements Controller {

    private TicketDB tickets;
    private PersonDB persons;

    public TicketController(TicketDB ticketDB){
        this.tickets = ticketDB;
    }

    @Override
    public int addEvenSplitTicket(Ticket t) {
        if (! (t instanceof EvenSplitTicket))
            return -1;
        if (tickets.find(t))
            return -1;
        tickets.addTicket(t);
        return 0;
    }

    @Override
    public int addUnevenSplitTicket(Ticket t) {
        if (! (t instanceof UnevenSplitTicket))
            return -1;
        if (tickets.find(t))
            return -1;
        tickets.addTicket(t);
        return 0;
    }

    /**
     * Adds a person based on its name
     * @param name name of the person we want to add
     * @return 0 = succes; -1 = person already exists
     */
    @Override
    public int addPerson(String name) {
        return this.persons.addPerson(new Person(name));
    }

    /**
     * removes a person based on its name
     * @param name name of the person we want to remove
     * @return 0 = succes; -1 = person doesn't exist
     */
    @Override
    public int removePerson(String name) {
        Optional<Person> p = this.persons.find(name);
        return p.map(person -> this.persons.removePerson(person)).orElse(-1);
    }
}
