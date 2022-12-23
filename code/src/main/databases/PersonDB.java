package databases;

import iterator.Aggregate;
import iterator.Iterator;
import person.Person;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Person DB
 * - Singleton (private constructor & public getInstance())
 */

public class PersonDB implements Aggregate {
    private static PersonDB instance = null;
    /**
     * DB is a LinkedList : easy addition & removal of Persons
     */
    private final LinkedList<Person> db;

    private final PropertyChangeSupport propertyChangeSupport;

    private PersonDB(){
        this.db = new LinkedList<>();
        this.propertyChangeSupport = new PropertyChangeSupport(this);
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
        Optional<Person> opt = this.db.stream().filter(person -> person.getName().equals(p.getName())).findAny();
        if (!opt.isPresent()){
            this.propertyChangeSupport.firePropertyChange("PersonAdded", db, p);
            this.db.add(p);
            return 0;
        }
        this.propertyChangeSupport.firePropertyChange("PersonAlreadyExists", db, p);
        return -1;
    }

    /**
     * @param p person to be removed
     * @return 0 = succes; -1 = p isn't in the db
     */
    public int removePerson(Person p){
        if (this.db.contains(p)){
            this.db.remove(p);
            this.propertyChangeSupport.firePropertyChange("PersonRemoved", p, db);
            return 0;
        }
        this.propertyChangeSupport.firePropertyChange("PersonDoesNotExist", db, p);
        return -1;
    }

    /**
     * Looks for a person with key=name
     * @param name name of the person we're looking for
     * @return Person class (if present)
     */
    public Optional<Person> find(String name){
        return this.db.stream().filter(p ->name.equals(p.getName())).findFirst();
    }

    @Override
    public Iterator createIt() {
        return new PersonIt();
    }

    /**
     * Used by iterator to get a person based on the index
     * @param index index of the iterator
     * @return Person at index
     */
    protected Person get(int index) {
        return this.db.get(index);
    }

    /**
     * Used by iterator to determine if person has neighbour
     * @return size of db
     */
    protected int size(){
        return this.db.size();
    }

    /**
     * Add a new PropertyChangeListener to the database
     * @param property The property on which it applies
     * @param propertyChangeListener The PropertyChangeListener to add
     */
    public void addPropertyChangeListener(String property, PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(property, propertyChangeListener);
    }
}
