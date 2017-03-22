package eu.net.ms.receiptbox;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public EventStorageEngine eventStorageEngine() {
		return new InMemoryEventStorageEngine();
	}
/*
	@Bean
	public EventStorageEngine eventStorageEngine(
			EntityManagerProvider entityManagerProvider,
			SpringTransactionManager springTransactionManager) {
		return new JpaEventStorageEngine(entityManagerProvider, springTransactionManager);
	}

	@Bean
	public SpringTransactionManager springTransactionManager(PlatformTransactionManager platformTransactionManager) {
		return new SpringTransactionManager(platformTransactionManager);
	}

	@Bean
	public EntityManagerProvider entityManagerProvider() {
		return new ContainerManagedEntityManagerProvider();
	}
*/
	@Bean
	public Serializer serializer() {
		Serializer serializer = new XStreamSerializer();
		return serializer;
	}
}
