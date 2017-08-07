# Fitch Message Standalone (Implementation 1)

- This is a standalone version of messaging application.

- Its built using technology stack below
	* Java 8 (with functional programming/lambda, dateTime, Objects API)
	* GIT (Source Repository/SCM)
	* STS (IDE from Spring)

- The source code is in the package com.fitch.standalone

- Setup Steps
	* Download/Clone the source from the repository @ https://github.com/nrmjbs/fitchMS ; branch: 		cr01-message-service-to-create-and-find-remove-message-by-type-ordered-by-priority-and-fifo
	* Setup the IDE with JRE 1.8
	* Import existing maven project in the IDE


- Steps to Run
	* Run MessageApplicationClient as a standalone application
	* Check for ASSERTIONS AGAINST EXPECTATIONS in 
		* MessageApplicationClient.test_Messages_Received_For_A_Type_Are_In_Order_Of_Priority_And_Fifo
		* MessageApplicationClient.test_Messages_That_Are_Created_And_Found_Match
			* The test print the response messages on the console. Copy paste lines with prefix 				[com.fitch.dto.MessageDto] into an editor of choice. Try to group messages by type but do retain the 				print order.
			* Confirm message order is by priority and creation date/time for a type

- Assumptions
	* Message priority order from highest to lowest [1,2,3]
	* Messages with same type, priority and create dateTime are duplicate and not allowed to be added


# Fitch Message Micro Service (Implementation 2)

- This is a Spring Boot micro service based web app.

- It abides by the micro service design and build principles.

- Its built using technology stack below
	* Java 8 (with functional programming/lambda, dateTime, Objects API)
	* Tomcat 8.5 (Embedded App Server)
	* Apache Derby 10.13.1.1 (Embedded DB)
	* JPA 2.1
	* JTA 1.2
	* Hibernate 5.0.12
	* Spring Boot 1.5.4
	* Spring Core/Context/Web/MVC/JDBC/AOP/TX 4.3.9
	* Spring Data JPA 1.11.4
	* Jackson 2.8.8 (JSON Marshalling/Unmarshalling)
	* Maven (Build Configuration)
	* GIT (Source Repository/SCM)
	* STS (IDE from Spring)
	* Postman (REST Client)


- Setup Steps
	* Download/Clone the source from the repository @ https://github.com/nrmjbs/fitchMS ; branch: 		cr01-message-service-to-create-and-find-remove-message-by-type-ordered-by-priority-and-fifo
	* Setup the IDE with JRE 1.8
	* Import existing maven project in the IDE


- Steps to Run [OPTION-1]
	* Run com.fitch.SpringBootFitchApplicationTests as a JUnit Test; should start embedded tomcat on port 8080 and context /fitchMS
	* Check for ASSERTIONS AGAINST EXPECTATIONS in 
		* SpringBootFitchApplicationTests.test_Messages_Received_For_A_Type_Are_In_Order_Of_Priority_And_Fifo
		* SpringBootFitchApplicationTests.test_Messages_That_Are_Created_And_Found_Match
			* The test print the response messages on the console. Copy paste lines with prefix 				[com.fitch.dto.MessageDto] into an editor of choice. Try to group messages by type but do 				retain the print order.
			* Confirm message order is by priority and creation date/time for a type


- Steps to Run [OPTION-2]
	* Run com.fitch.SpringBootFitchApplication as a Java Application ; should start embedded tomcat on port 		8080 and context /fitchMS


- Steps to add a Message
	* Send a POST request to add a message 
		to: http://localhost:8080/fitchMS/messages 
		with raw body:
			{
				"messageText" : "Hello Message",
		  		"messageType" : "RED", 
		  		"messagePriority" : 1
			}
		content-type: application/json	
	* Should get a Status of 200 OK and the newly created message in JSON format in the body of the response
		{
			"messageText": "Hello Message",
			"messageType": "RED",
			"messagePriority": 1,
			"messageCreateDateTime": "2017-08-04T20:11:10.144"
		}
	* If the raw body contains a messageType other than RED, BLUE, YELLOW; a validation error is 		thrown back with a 		Status/Message of 400 Bad Request in the header and 400 Message type 		<messageType> not supported.
	* If the raw body contains a messagePriority other than 1, 2, 3; a validation error is thrown back 		with a Status/Message of 400 Bad Request in the header and 400 Message priority 		<messagePriority> not supported.


- Steps to find and remove a Message by type. Messages are first matched by type. If more than one 	found, then they are filtered to 	find the one with the highest priority. Still if more than one found, then they are filtered to find the one that was created 	first. 	That message is returned and also removed from the store.
	* Send a GET request to get a message by type
		to: http://localhost:8080/fitchMS/messages?type=RED
	* Should get the message in JSON format with the highest priority in FIFO order
		{
			"messageText": "Hello Message",
			"messageType": "RED",
			"messagePriority": 1,
			"messageCreateDateTime": "2017-08-04T20:11:10.144"
		}
	* If a message for a given type doesn't exist, a 404 Not Found is returned in the header and 404 Message with type <type> not found 		; in the body of the response.
	* If the query param contains a messageType other than RED, BLUE, YELLOW; a validation error is thrown back with a 		Status/Message of 400 Bad Request in the header and 400 Message type <messageType> not supported.


- Assumptions
	* Message priority order from highest to lowest [1,2,3]


- Known Issues
	* Since the embedded DB doesn't allow row level locking during SELECT as Oracle does using SELECT FOR UPDATE clause, multiple 		threads via multiple nodes/service-instances in a cluster could request a message for the same type concurrently and end up 		getting message with same priority/fifo order. 

- Workarounds
	* Within a single node/service-instance, multiple request threads are synchronized to prevent getting message with same 		priority/fifo order.


- Missing Features
	* The callback feature has not been implemented as such but while using the Java 8 functional programming feature, loads
		of lambda expressions are written as callback while collections are streamed or iterated.

