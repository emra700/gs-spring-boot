package eu.net.ms.receiptbox;

import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

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

	@Bean
	public Serializer serializer() {
		Serializer serializer = new XStreamSerializer();
		return serializer;
	}
}
