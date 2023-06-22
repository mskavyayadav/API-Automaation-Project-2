package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;
import junit.framework.Assert;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("***********************  Creating User  ****************************");
		
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***********************  User is Created  ****************************");
		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		
		logger.info("***********************  Reading User Info  ****************************");
		
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername()); 
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***********************  User Info is Displayed  ****************************");
	}
	

	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("***********************  Updated User  ****************************");
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload); 
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***********************  User is Upated ****************************");
		
		
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername()); 
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		
		}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		logger.info("***********************  Deleting User  ****************************");
		
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername()); 
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***********************  User is deleted  ****************************");
		
	}
	
	}
