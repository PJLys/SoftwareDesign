package controller;


import databases.*;
import debts.Debt;
import factory.TicketFactory;
import iterator.Iterator;
import person.Person;
import tickets.*;
import tickets.evensplit.EvenSplitTicket;
import tickets.unevensplit.UnevenEntry;
import tickets.unevensplit.UnevenSplitTicket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
        if (!p.isPresent())
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
        if (!p.isPresent())
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

    public ArrayList<Debt> calcDebt(){
        Iterator tit = TicketDB.getInstance().createIt();
        Iterator pit = PersonDB.getInstance().createIt();

        Map<Person, Double> debts = new HashMap<Person, Double>();
        while (pit.hasNext())
            debts.put((Person) pit.next(),0.0);

        while(tit.hasNext()){
            Ticket t = (Ticket) tit.next();
            switch (t.getClass().getName()){
                case "UnevenSplitTicket" : {
                    UnevenSplitTicket ust = (UnevenSplitTicket) t;
                    Person payer = ust.getPayer();
                    ArrayList<UnevenEntry> entries = ust.getEntries();
                    for (UnevenEntry e:entries) {
                        debts.replace(e.p, debts.get(e.p) - e.val);
                        debts.replace(payer, debts.get(payer) + e.val);
                    }
                }
                case "EvenSplitTicket" : {
                    // Convert ticket to EST, so we can get all specific info
                    assert t instanceof EvenSplitTicket;
                    EvenSplitTicket est = (EvenSplitTicket) t;
                    Person payer = est.getPayer();
                    double ppp = est.getPpp();
                    ArrayList<Person> people = est.getPersons();
                    // Add (price per person) * (every person paid for) + (previous amount to debts)
                    debts.replace(payer, debts.get(payer)+ppp* people.size());
                    // Subtract ppp from every person paid for
                    for (Person p:people)
                        debts.replace(p,debts.get(p)-ppp);
                }
            }
        }

        ArrayList<Debt> transactions = new ArrayList<Debt>();
        Set<Person> keyset = debts.keySet();
        for (Person p1:keyset){
            double bal1 = debts.get(p1);
            if (bal1<0) {
                for (Person p2:keyset){
                    if (p1!=p2){
                        double bal2 = debts.get(p2);
                        if (bal1<-bal2){
                            bal1 += bal2;
                            debts.replace(p1, bal1);
                            debts.replace(p2, 0.0);
                            transactions.add(new Debt(p1, p2, bal2));
                        } else {
                            debts.replace(p1, 0.0);
                            debts.replace(p2, bal1+bal2);
                            transactions.add(new Debt(p1, p2, -bal1));
                            break;
                        }
                    }
                }

            }
        }
        return transactions;
    }
}
