package aaa.pfa.carAuctionBackend;

import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarAuctionBackendApplication
		implements CommandLineRunner
{

	public static void main(String[] args) {

		SpringApplication.run(CarAuctionBackendApplication.class,
				args);

	}
	private final UserRepository userRepository;
	private final CarRepository carRepository;

	public CarAuctionBackendApplication(UserRepository urp, CarRepository carRepository){
		this.userRepository = urp;
		this.carRepository = carRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		User Asuka = new User(
				"frenchgirllover69",
				"$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9f9q0e4bRadue",
				"USER",
				"Asuka",
				"Kazama");
		User lili = new User("yellowever",
				"$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9mP9vpMu0ssKi8GW",
				"ADMIN",
				"Emilie",
				"De Rochefort");
		// Username: user, password: user

		User a = new User("a",
				"$2a$10$Zbtg3ybemOLxxvZ0ytlXs.nKOdaT6OlxNMjWOKqs7H4q4jRPnpSA6",
				"USER",
				"firstName",
				"lastName");

		userRepository.save(Asuka);
// Username: admin, password: admin
		userRepository.save(lili);
		userRepository.save(a);
		Car corolla = new Car("Toyota", "Corolla", 2000, 1998);
		Car f150 = new Car("Ford", "F-150", 8000, 2004);
		corolla.setUser(Asuka);
		f150.setUser(lili);
		carRepository.save(corolla);
		//carRepository.save(f150);
		carRepository.save(f150);
	}



}
