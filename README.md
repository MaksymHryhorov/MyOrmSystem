# MyOrmSystem
## Getting started
* Use command git clone https://github.com/MaksymHryhorov/MyOrmSystem.git
* Add your file to the resource folder. It can be (json, xml, csv) format

* You should also change the name of fields in the model class

For example it's my model

![image](https://user-images.githubusercontent.com/84277122/194314678-bfce6690-a78b-4f94-bf35-653d0f335074.png)

* After all the above actions let's go to the Parser class and change "process" method
It should have
```java
File fileFormat = new File("src/main/resources/format.");
DataReadWriteSource<?> source = new FileReadWriteSource(fileFormat);
result = ORM.readAll(source, Model.class);
```
Comment: You must change Person class is your model class

If you want parse 'json' format
```java
File json = new File("src/main/resources/format.json");
DataReadWriteSource<?> csvFile = new FileReadWriteSource(json);
```

If you want parse 'csv' format
```java
File csv = new File("src/main/resources/format.csv");
DataReadWriteSource<?> csvFile = new FileReadWriteSource(csv);
```

If you want parse 'xml' format
```java
File xml = new File("src/main/resources/format.xml");
DataReadWriteSource<?> csvFile = new FileReadWriteSource(xml);
```

* If you want see the result in console write this in "process" method
```java
  for (Person person : result) {
    System.out.println(person);
  }
```

* If you want to write a model into database
```java
WriteToDatabase writeToDatabase = new WriteToDatabase();
writeToDatabase.writeToDataBase(model list, class.class);

Comment: Where Person is your model class
```
