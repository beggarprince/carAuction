package aaa.pfa.carAuctionBackend;

import aaa.pfa.carAuctionBackend.controller.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CarAuctionBackendApplicationTests {

	@Autowired
	private UserController userController;
	@Test
	@DisplayName("First example test case")
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

}
