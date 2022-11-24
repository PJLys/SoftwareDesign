package controller;

import databases.*;
import factory.TicketFactory;
import person.Person;
import tickets.evensplit.EvenSplitTicket;
import tickets.Ticket;
import tickets.unevensplit.UnevenSplitTicket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class TicketController implements Controller {

    private final TicketDB tickets;
    private final PersonDB persons;
    private final TicketFactory tf;

    public TicketController(TicketDB ticketDB, PersonDB personDB, TicketFactory factory){
        this.tickets = ticketDB;
        this.persons = personDB;
        this.tf = factory;
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
        return p.map(this.persons::removePerson).orElse(-1);
    }

    @Override
    public Ticket makeEvenSplitTicket() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give payer's name: ");
        String name = reader.readLine();
        Optional<Person> p = this.persons.find(name);
        if (p.isEmpty())
            throw new NoSuchElementException("Person doesn't exist");
        Person payer = p.get();

        System.out.println("Give attendant's name (<enter> to finish): ");
        name = reader.readLine();
        ArrayList<Person> attendants = new ArrayList<Person>();
        while (!Objects.equals(name, "")){
            p = this.persons.find(name);
            if (p.isPresent()) {
                attendants.add(p.get());
            } else {
                System.out.println("Person couldn't be found");
            }
            System.out.println("Give attendant's name (<enter> to finish): ");
            name = reader.readLine();
        }

        return tf.createEvenSplitTicket(payer, attendants);
    }

    @Override
    public Ticket makeUnevenSplitTicket() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Give payer's name: ");
        String name = reader.readLine();
        Optional<Person> p = this.persons.find(name);
        if (p.isEmpty())
            throw new NoSuchElementException("Person doesn't exist");
        Person payer = p.get();

        System.out.println("Give attendant's name (<enter> to finish): ");
        name = reader.readLine();
        ArrayList<Person> attendants = new ArrayList<Person>();
        while (!Objects.equals(name, "")){
            p = this.persons.find(name);
            if (p.isPresent()) {
                attendants.add(p.get());
            } else {
                System.out.println("Person couldn't be found");
            }
            System.out.println("Give attendant's name (<enter> to finish): ");
            name = reader.readLine();
        }
        return tf.createUnevenSplitTicket(payer, attendants);
    }

    @Override
    public Person getPerson(String name) {
        Optional<Person> p = this.persons.find(name);
        return p.orElse(null);
    }
}
