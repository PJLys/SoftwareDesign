package databases;

import person.Person;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Person DB
 * - Singleton (private constructor & public getInstance())
 */

public class PersonDB {
    private static PersonDB instance = null;
    /**
     * DB is a LinkedList : easy addition & removal of Persons
     */
    private LinkedList<Person> db;

    private PersonDB(){
        this.db = new LinkedList<>();
    }

    public static PersonDB getInstance() {
        if (instance==null)
            instance = new PersonDB();
        return instance;
    }

    /**
     * @param p person to be added
     * @return 0 = succes; -1 = t is a duplicate
     */
    public int addPerson(Person p){
        if (!this.db.contains(p)){
            this.db.add(p);
            return 0;
        }
        return -1;
    }

    /**
     * @param p person to be removed
     * @return 0 = succes; -1 = p isn't in the db
     */
    public int removePerson(Person p){
        if (this.db.contains(p)){
            this.db.remove(p);
            return 0;
        }
        return -1;
    }

    public Optional<Person> find(String name){
        return this.db.stream().filter(p ->name.equals(p.getName())).findFirst();
    }

}
