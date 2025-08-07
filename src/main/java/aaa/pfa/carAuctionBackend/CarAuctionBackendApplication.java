package aaa.pfa.carAuctionBackend;

import aaa.pfa.carAuctionBackend.model.Car;
import aaa.pfa.carAuctionBackend.model.User;
import aaa.pfa.carAuctionBackend.repository.CarRepository;
import aaa.pfa.carAuctionBackend.repository.CarRepositoryCustomQuery;
import aaa.pfa.carAuctionBackend.repository.ProductPictureRepository;
import aaa.pfa.carAuctionBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarAuctionBackendApplication
{

	public static void main(String[] args) {

		System.out.println("Main running");
		SpringApplication.run(CarAuctionBackendApplication.class,
				args);

	}
	private final UserRepository userRepository;
	private final CarRepository carRepository;
	private final ProductPictureRepository pictureRepository;
	private final CarRepositoryCustomQuery carRepositoryCustomQuery;


	public CarAuctionBackendApplication(UserRepository urp,
										CarRepository carRepository,
										ProductPictureRepository pictureRepository,
                                        @Qualifier("carRepository") CarRepositoryCustomQuery c
	){
		this.userRepository = urp;
		this.carRepository = carRepository;
		this.pictureRepository = pictureRepository;
		this.carRepositoryCustomQuery = c;

//		var pList = pictureRepository.findAll();
//		pList.forEach(p->{
//			//System.out.println( "PICTURE ID:" + p.getId());
//			System.out.println("---- ProductPicture ----");
//			System.out.println("ID: " + p.getId());
//			System.out.println("Owner: " + p.getOwner());
//			System.out.println("ImageData: " + p.getImageData());  // Binary object's toString()
//			System.out.println();
//		});
	}

}
