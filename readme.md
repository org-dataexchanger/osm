# OSM - Object Sheet Mapping
OSM is a project aimed to export and import data from object to sheet and vice-versa in a standardized format. It is currently under development.

### How to start
To play with OSM, clone the repository and build with 
```
mvn clean install
```
Now from test packages, find our ```SheetManagerFactoryTest``` and run the test to understanding what is happening.

#### Exporting Object to Sheet 
#### 1. When one address belongs to many employees.
##### Initialization
Scan SheetEntity classes
```
    SheetManagerFactory sheetManagerFactory = new SheetManagerFactory("org.dataexchanger.osm.example");
```
##### Preparing Objects to Export
Create Employee and Address objects. Different employees can have same address.
```
        Address address1 = new Address();
        address1.setId(3);
        address1.setHouse("Forest Lodge");
        address1.setStreet("S.S. Academy Road");

        Address address2 = new Address();
        address2.setId(4);
        address2.setHouse("Avijan 9/2");
        address2.setStreet("S.S. Academy Road");

        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Mainul");
        e1.setAge(25);
        e1.setAddress(address1);
        employees.add(e1);

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setName("Hasan");
        e2.setAge(26);
        e2.setAddress(address2);
        employees.add(e2);

        Employee e3 = new Employee();
        e3.setId(3L);
        e3.setName("Siam");
        e3.setAge(16);
        e3.setAddress(address1);
        employees.add(e3);
```
##### Preparing the workbook
```
        for (Employee e : employees) {
            sheetManagerFactory.prepareWorkbook(e);
        }
```
##### Export as file
```
    sheetManagerFactory.writeWorkbookAsFile();
```

##### Get Byte Array to send as server response
```
    byte[] bytes = sheetManagerFactory.getByteContent();
```
