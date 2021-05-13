## Description
This is a suite of microservices that exposes the following functionalities:

1. Create a customer and return the generated customer id.
2. Create an Account using the customer id if it doesn't have one, and a Transaction if the amount sent is not zero.
3. List all transactions created with the customer id.

### Tools used
- Java 11
- SpringBoot Framework 2.4.2
- Spring WebClient 
- Gradle 6.7.1
- Junit 5
- Angular 11.2.13
- Git 2.25.1

### How to run the Application
1. Checkout the project from the github repository https://github.com/charles1303/acct-mgmt-microservices.
2. Run gradle build in the parent folder to build all the microservices and run the corresponding tests with following command


	gradle clean build

3. In each of the microservices' folder execute the docker-compose command. For example for the account-service, cd into the folder and run the command below


	docker-compose up
	
This command will build a docker image to your local registry and deploy it along with its dependent service(the database) as dockerized container.

4. Repeat the step 3 for customer-service and transaction-service.
5. For the front end application, cd into the acct-mgmt-front-end folder and run the following commands sequentially


	npm install
	ng build
    ng serve


6. We can then access the application using the url http://localhost:4200
7. Click on the Add Customer link to create a customer with your supplied firstname and surname, and use the generated customer id that will be used for the subsequent operations below
8. Click on the Add account and create transaction link and supply the customer id generated, and an amount to create an account and transaction.
9. You can repeat step 8 as many times as you like using the same customer id with different/or same amounts to have several transactions.
10. Click on the View account details and transactions link and supply the customer id to view the account details and the associated transactions.


### Constraints
1. The customer can have only a single account.


### CI/CD Integration
In a scenario where we implement and end to end CI/CD implementations where we can integrate to either CircleCI or Github Actions, 
the build strategy and the docker-compose.yml file configurations will be slightly different for the services. 
Here a developer pushes changes to branch and this action triggers the following steps in the CI/CD pipeline:

1. The gradle build task is triggered
2. The tests are run
3. If any of the tests fails the CI/CD pipeline exits else it generates the deployment artifact and proceeds to the next step.
4. Docker command is triggered to build an image using the generated deployment artifact from step 3
5. The image is tagged by Docker with a tag number by using a strategy (for example a combination of the commit hash of the code, timestamp, and the target environment name (e.g staging))
6. For the final deployment in the target environment of the branch, the docker-compose.yml is executed but this time around it pulls the tagged image 
   rather than building the Docker image as in our current case.

The snippet below in the docker-compose.yml

	build:
      context: ./
      dockerfile: Dockerfile

will be replaced with

	image: {SERVICE_NAME}:{TAG_NUMBER}

We can pass the variables as a command line to the docker-compose command like below

	SERVICE_NAME=account-service TAG_NUMBER=234360192447323 docker-compose up

7. Step 6 can be triggered either automatically after step 5 in the pipeline or manually depending on the target deployment environment.
	
8. Once steps 1-5 are successful, and the pushed code has gone through the code review process and approved, the branch is merged 
   to the production branch and steps 1 through 6 are repeated for the target environment of the production branch.
9. In a team, code push would be done based on a particular task so that code changes are kept to a minimal for easier code reviewing by the team members.

### Improvements
1. We can implement Security using OAuth2 for inter-microservices communication by way of using JWTs.
2. We can allow the customer to have multiple accounts, but we will have to send both the customerId and accountNumber to the APIs.
3. The creation of transactions is a good candidate for implementing the Messaging pattern to reduce latency and improve scalability. 
   Hence, we can replace the WebclientRequest call to the transaction service with a Kafka Publisher that publishes the payload 
   to a Kafka queue and implement a Kafka Consumer on the transaction service to consume and process the message accordingly.
4. The front end UI can be improved in areas of layouts and themes.
5. I have included the environmental variables value in the source code for the purpose of testing. 
   Ideally these values should be stored somewhere and referenced from there. For example, we can change the location of the .env file in the docker-compose.yml files to secret location.
6. We can use migration frameworks such as Liquibase and Flyway to manage changes to our database schema and data rather than relying on JPA as this approach
is much safer and easier for rollbacks.
