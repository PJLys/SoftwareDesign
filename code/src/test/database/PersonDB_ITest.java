package database;

import databases.PersonDB;
import iterator.Iterator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import person.Person;

import java.util.Optional;

public class PersonDB_ITest {
    public PersonDB_ITest() {}
    PersonDB pdb;
    Person alex = new Person("Alex");

    @Before
    public void initialize() {
        pdb = PersonDB.getInstance();
    }

    @Test
    public void t_addRemove() {
        // Empty the DB, because this isn't the case with multiple tests
        try {
            Iterator pit = pdb.createIt();
            while(pit.hasNext())
                pit.next();
                pit.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals("Shouldn't be able to remove person in empty DB",-1, pdb.removePerson(alex));
        Assert.assertEquals("Should be able to add person to db", 0, pdb.addPerson(alex));
        Assert.assertEquals("Shouldn't be able to add the same person twice", -1, pdb.addPerson(alex));
        Assert.assertEquals("Should be able to remove added person in  DB",0, pdb.removePerson(alex));
    }

    @Test
    public void t_find() {
        Optional<Person> returnedPerson = pdb.find("Alex");
        Assert.assertFalse("Find by name should return empty if Person not in DB", returnedPerson.isPresent());
        Assert.assertEquals("Should be able to add person to db", 0, pdb.addPerson(alex));
        returnedPerson = pdb.find("Alex");
        Assert.assertTrue("Added person should be found by name", returnedPerson.isPresent());
        Assert.assertEquals("Returned person should be the same as added person",alex, returnedPerson.get());
    }
}
