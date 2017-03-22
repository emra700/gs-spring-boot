package eu.net.ms.receiptbox.model.aggregates;

import eu.net.ms.receiptbox.model.commands.GetReceiptCommand;
import eu.net.ms.receiptbox.model.commands.InsertReceiptCommand;
import eu.net.ms.receiptbox.model.commands.InsertReceiptCustomerCommand;
import eu.net.ms.receiptbox.model.commands.UpdateAppIdOnReceiptCommand;
import eu.net.ms.receiptbox.model.events.ReceiptAppIdUpdatedEvent;
import eu.net.ms.receiptbox.model.events.ReceiptCustomerInsertedEvent;
import eu.net.ms.receiptbox.model.events.ReceiptFetchEvent;
import eu.net.ms.receiptbox.model.events.ReceiptInsertedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import lombok.*;
import javax.persistence.*;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * Created by petsk on 2017-03-22.
 */

@Aggregate
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    @AggregateIdentifier
    @Id
    @Getter
    private String receiptId;
    @Basic
    @Getter
    private String customerId;
    @Basic
    @Getter
    private String appId;
    @Column(columnDefinition = "varchar(1024)")
    @Getter
    private String receiptData;

    @CommandHandler
    public Receipt(InsertReceiptCommand command) {
        apply(new ReceiptInsertedEvent(
                command.getReceiptId(),
                command.getReceiptData()
        ));
    }

    @CommandHandler
    public Receipt(InsertReceiptCustomerCommand command) {
        apply(new ReceiptCustomerInsertedEvent(
                command.getReceiptId(),
                command.getCustomerId(),
                command.getReceiptData()
        ));
    }

    @CommandHandler
    public void handle(UpdateAppIdOnReceiptCommand command) {
        apply(new ReceiptAppIdUpdatedEvent(command.getReceiptId(), command.getAppId()));
    }

    @CommandHandler
    public void handle(GetReceiptCommand command) {
        apply(new ReceiptFetchEvent(command.getReceiptId(), command.getAppId()));
    }

    @EventSourcingHandler
    private void on(ReceiptInsertedEvent event) {
        this.receiptId = event.getReceiptId();
    }

    @EventSourcingHandler
    private void on(ReceiptCustomerInsertedEvent event) {
        this.receiptId = event.getReceiptId();
    }

    @EventSourcingHandler
    private void on(ReceiptAppIdUpdatedEvent event) {}
}
