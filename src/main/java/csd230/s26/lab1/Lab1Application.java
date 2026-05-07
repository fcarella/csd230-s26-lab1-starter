package csd230.s26.lab1;

import com.github.javafaker.Faker;
import csd230.s26.lab1.entities.BookEntity;
import csd230.s26.lab1.entities.DiscMagEntity;
import csd230.s26.lab1.entities.MagazineEntity;
import csd230.s26.lab1.entities.TicketEntity;
import csd230.s26.lab1.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Lab1Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(BookRepository bookRepository,
								   MagazineRepository magazineRepository,
								   DiscMagEntityRepository discMagRepository,
								   TicketEntityRepository ticketRepository,
								   ProductRepository productRepository) {
		return args -> {
			Faker faker = new Faker();

			// Create Books
			for (int i = 0; i < 3; i++) {
				BookEntity book = new BookEntity(
						faker.book().author(),
						faker.book().title(),
						Double.parseDouble(faker.commerce().price(10.0, 50.0)),
						faker.number().numberBetween(1, 100)
				);
				bookRepository.save(book);
			}

			// Create 3 Magazines
			for (int i = 0; i < 3; i++) {
				MagazineEntity mag = new MagazineEntity(
						faker.number().numberBetween(10, 100),       // Order Qty
						LocalDateTime.now().minusDays(i),            // Current Issue (varying dates)
						faker.book().genre() + " Magazine",          // Title
						Double.parseDouble(faker.commerce().price(5.0, 20.0)), // Price
						faker.number().numberBetween(10, 500)        // Copies
				);
				magazineRepository.save(mag);
				System.out.println("Saved Magazine: " + mag.getTitle());
			}

			System.out.println("Database initialization complete with Books and Magazines.");

			// Create 3 Tickets
// Create 3 Tickets
			for (int i = 0; i < 3; i++) {
				// Using a combination to make it sound like a specific event ticket
				String eventName = faker.commerce().department() + " " + faker.company().suffix();

				TicketEntity ticket = new TicketEntity(
						eventName + " Ticket",
						Double.parseDouble(faker.commerce().price(5.0, 100.0))
				);
				ticketRepository.save(ticket);
				System.out.println("Saved Ticket: " + ticket.getDescription());
			}
			// Create 3 DiscMags
			for (int i = 0; i < 3; i++) {
				DiscMagEntity discMag = new DiscMagEntity(
						faker.bool().bool(),
						faker.number().numberBetween(10, 100),
						LocalDateTime.now().minusDays(i),
						faker.book().title() + " (with Disc)",
						Double.parseDouble(faker.commerce().price(10.0, 30.0)),
						faker.number().numberBetween(5, 50)
				);
				discMagRepository.save(discMag);
				System.out.println("Saved DiscMag: " + discMag.getTitle());
			}

			System.out.println("\n--- Listing All Products ---");

			productRepository.findAll().forEach(product -> {
				System.out.println(product.toString());
//				System.out.print("ID: " + product.getId() + " | Type: " + product.getClass().getSimpleName());
//
//				if (product instanceof BookEntity) {
//					BookEntity book = (BookEntity) product;
//					System.out.println(" | Title: " + book.getTitle() + " | Author: " + book.getAuthor());
//				} else if (product instanceof MagazineEntity) {
//					MagazineEntity mag = (MagazineEntity) product;
//					System.out.println(" | Title: " + mag.getTitle() + " | Issue: " + mag.getCurrentIssue());
//				} else {
//					System.out.println(" | Generic Product");
//				}
			});
		};
	}
}