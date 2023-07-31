# DATABASE MIGRATION USING LIQUIBASE

## Step 1: Include following dependency

```xml

		<dependency>
			<groupId>org.liquibase</groupId>
			<artifactId>liquibase-core</artifactId>
		</dependency>

```

## Step 2: Create a liquibase.properties file in resources/liquibase.properties

```
	url=${jdbc:mysql://localhost:3306/event-management-database}
	username=${root}
	password=${adminGaj}
	changeLogFile=src/main/resources/sql/changelog-master.xml
```


## Step 3: Create a folder in resources/Sql

###  Include changelog-master.xml in resources/Sql

```xml
	
	<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.2.xsd">

    	<include file="sql/changelog-users-table.sql"/>
    
	</databaseChangeLog>
```
Note: Update all the changelog files in <include " "/> above

