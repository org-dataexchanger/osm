package org.dataexchanger.osm;

import org.dataexchanger.osm.example.Address;
import org.dataexchanger.osm.example.Contact;
import org.dataexchanger.osm.example.Employee;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SheetManagerFactoryTest {

    private SheetManagerFactory sheetManagerFactory;
    @Before
    public void setup() throws IOException, ClassNotFoundException {
        sheetManagerFactory = new SheetManagerFactory("org.dataexchanger.osm.example");
    }

    @Test
    public void test_export() throws IllegalAccessException, IOException {

        Address address1 = new Address();
        address1.setId(3);
        address1.setHouse("Forest Lodge");
        address1.setStreet("S.S. Academy Road");

        Address address2 = new Address();
        address2.setId(4);
        address2.setHouse("Avijan 9/2");
        address2.setStreet("S.S. Academy Road");

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setContactName("Mainul Hasan 1");
        contact1.setEmail("mainuls18+001@gmail.com");
        contact1.setPhone("+8801634440001");
        Contact contact2 = new Contact();

        contact1.setId(2L);
        contact1.setContactName("Mainul Hasan 2");
        contact1.setEmail("mainuls18+002@gmail.com");
        contact1.setPhone("+8801634440002");

        Contact contact3 = new Contact();
        contact1.setId(3L);
        contact1.setContactName("Mainul Hasan 3");
        contact1.setEmail("mainuls18+003@gmail.com");
        contact1.setPhone("+8801634440003");

        Contact contact4 = new Contact();
        contact1.setId(4L);
        contact1.setContactName("Mainul Hasan 4");
        contact1.setEmail("mainuls18+004@gmail.com");
        contact1.setPhone("+8801634440004");

        Contact contact5 = new Contact();
        contact1.setId(5L);
        contact1.setContactName("Mainul Hasan 5");
        contact1.setEmail("mainuls18+005@gmail.com");
        contact1.setPhone("+8801634440005");

        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Mainul");
        e1.setAge(25);
        e1.setAddress(address1);
        List<Contact> contactsForEmp1 = new ArrayList<>();
        contactsForEmp1.add(contact1);
        contactsForEmp1.add(contact3);
        contactsForEmp1.add(contact5);
        e1.setContacts(contactsForEmp1);
        employees.add(e1);

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setName("Hasan");
        e2.setAge(26);
        e2.setAddress(address2);
        List<Contact> contactsForEmp2 = new ArrayList<>();
        contactsForEmp2.add(contact2);
        contactsForEmp2.add(contact3);
        contactsForEmp2.add(contact5);
        e1.setContacts(contactsForEmp2);
        employees.add(e2);

        Employee e3 = new Employee();
        e3.setId(3L);
        e3.setName("Siam");
        e3.setAge(16);
        e3.setAddress(address1);
        List<Contact> contactsForEmp3 = new ArrayList<>();
        contactsForEmp3.add(contact2);
        contactsForEmp3.add(contact1);
        contactsForEmp3.add(contact4);
        e1.setContacts(contactsForEmp3);
        employees.add(e3);

        for (Employee e : employees) {
            sheetManagerFactory.prepareWorkbook(e);
        }
        sheetManagerFactory.writeWorkbookAsFile();

        // Or if you want to send the file over the network,
        // you will need this byte array
//        byte[] bytes = sheetManagerFactory.getByteContent();
    }
}
