package databases;

import person.Person;

import java.util.LinkedList;

public class PersonDB {
    private PersonDB instance = null;
    private LinkedList<Person> db = new LinkedList<Person>();
    private PersonDB(){}

    public PersonDB getInstance() {
        if (instance==null)
            this.instance = new PersonDB();
        return this.instance;
    }

    public int addPerson(Person p){
        if (!this.db.contains(p)){
            this.db.add(p);
            return 0;
        }
        return -1;
    }

    public int removePerson(Person p){
        if (this.db.contains(p)){
            this.db.remove(p);
            return 0;
        }
        return -1;
    }
}
