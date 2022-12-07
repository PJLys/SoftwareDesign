package database;

import databases.PersonDB;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import person.Person;

import java.lang.reflect.Field;
import java.util.LinkedList;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PersonDB.class)

public class PersonDB_UTest {
    private Field field;
    private PersonDB testDB;
    private LinkedList<Person> mock_db;
    private Person mockPerson;
    public PersonDB_UTest(){}
    @Before
    public void initialize() throws NoSuchFieldException {
        this.field = PersonDB.class.getDeclaredField("db");
        this.field.setAccessible(true);
        this.testDB = PersonDB.getInstance();
        this.mock_db = (LinkedList<Person>) Mockito.mock(LinkedList.class);
        this.mockPerson = Mockito.mock(Person.class);
    }

    @Test
    public void t_addPerson() throws IllegalAccessException {
        this.field.set(this.testDB, this.mock_db);
        this.testDB.addPerson(this.mockPerson);
        Mockito.verify(this.mock_db, Mockito.times(1)).add(this.mockPerson);
    }
}
