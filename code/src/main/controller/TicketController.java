package controller;


import databases.*;
import debts.Transaction;
import factory.TicketFactory;
import iterator.Iterator;
import person.Person;
import tickets.*;
import tickets.evensplit.EvenSplitTicket;
import tickets.unevensplit.UnevenEntry;
import tickets.unevensplit.UnevenSplitTicket;

import java.util.*;

public class TicketController implements Controller {

    private final TicketDB tickets;
    private final PersonDB persons;
    private final TicketFactory tf;

    public TicketController(TicketDB ticketDB, PersonDB personDB, TicketFactory factory)  {
        this.tickets = ticketDB;
        this.persons = personDB;
        this.tf = factory;
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

    /**
     * Translates inputs into usable types for creating an even split ticket,
     * calls the factory,
     * adds the created ticket to the DB.
     * @param type Expense type (Cab, plane, concert, ...)
     * @param payer_name Person who paid
     * @param total Total amount paid
     * @param attendants list of people who were paid for
     * @return newly added ticket
     */
    @Override
    public Ticket makeEvenSplitTicket(ExpenseType type, String payer_name, double total, List<String> attendants) {
        ArrayList<Person> list = new ArrayList<>();
        attendants.forEach(p -> list.add(this.getPerson(p)));
        Ticket ticket = this.tf.createEvenSplitTicket(type, this.getPerson(payer_name), total, list);
        this.tickets.addTicket(ticket);
        return ticket;
    }

    /**
     * Translates inputs into usable types for creating an uneven split ticket,
     * calls the factory,
     * adds the created ticket to the DB.
     * @param type Expense type (Cab, plane, concert, ...)
     * @param payer_name Person who paid
     * @param named_entries Mapping of people names with their respective debt
     * @return Newly added ticket
     */
    @Override
    public Ticket makeUnevenSplitTicket(ExpenseType type, String payer_name, HashMap<String, Double> named_entries) {
        ArrayList<UnevenEntry> entries = new ArrayList<>();
        for (String name : named_entries.keySet()) {
            entries.add(new UnevenEntry(this.getPerson(name), named_entries.get(name)));
        }
        Ticket ticket = this.tf.createUnevenSplitTicket(type, this.getPerson(payer_name), entries);
        this.tickets.addTicket(ticket);
        return ticket;
    }

    /**
     * Will return a person based on its name
     * @param name name of person
     * @return a person or a null pointer
     */
    @Override
    public Person getPerson(String name) {
        Optional<Person> p = this.persons.find(name);
        return p.orElse(null);
    }

    /**
     * lists people's names
     * @return all names
     */
    @Override
    public ArrayList<String> getPersonStringList() {
        PersonIt personIt = (PersonIt) persons.createIt();
        ArrayList<String> personStringList = new ArrayList<>();
        Person person;
        while (personIt.hasNext()) {
            person = (Person) personIt.next();
            personStringList.add(person.getName());
        }
        return personStringList;
    }

    /**
     * 1) Iterate through people to create a debt hashmap
     * 2) Iterate through tickets and update debt hashmap accordingly
     * 3) Iterate through the hashmap in order to find who owes who.
     * @return list of transactions to be done
     */
    public ArrayList<Transaction> calcDebt(){
        Iterator tit = TicketDB.getInstance().createIt();
        Iterator pit = PersonDB.getInstance().createIt();

        Map<Person, Double> debts = new HashMap<Person, Double>();
        while (pit.hasNext())
            debts.put((Person) pit.next(),0.0);

        while(tit.hasNext()){
            Ticket t = (Ticket) tit.next();
            if (t instanceof UnevenSplitTicket) {
                UnevenSplitTicket ust = (UnevenSplitTicket) t;
                Person payer = ust.getPayer();
                ArrayList<UnevenEntry> entries = ust.getEntries();
                for (UnevenEntry e:entries) {
                    debts.replace(e.p, debts.get(e.p) - e.val);
                    debts.replace(payer, debts.get(payer) + e.val);
                }
            }
            else {
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

        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Set<Person> keyset = debts.keySet();
        for (Person p1:keyset){
            double bal1 = debts.get(p1);
            if (bal1<0) {
                for (Person p2:keyset){
                    if (p1!=p2){
                        double bal2 = debts.get(p2);
                        if (bal1<-bal2 & bal2>0){
                            bal1 += bal2;
                            debts.replace(p1, bal1);
                            debts.replace(p2, 0.0);
                            transactions.add(new Transaction(p1, p2, bal2));
                        } else if (bal2>0) {
                            debts.replace(p1, 0.0);
                            debts.replace(p2, bal1+bal2);
                            transactions.add(new Transaction(p1, p2, -bal1));
                            break;
                        }
                    }
                }
            }
        }
        return transactions;
    }
}
