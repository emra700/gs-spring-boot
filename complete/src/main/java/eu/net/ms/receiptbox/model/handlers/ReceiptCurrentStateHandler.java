package eu.net.ms.receiptbox.model.handlers;

import eu.net.ms.receiptbox.model.aggregates.Receipt;
import eu.net.ms.receiptbox.model.commands.UpdateAppIdOnReceiptCommand;
import eu.net.ms.receiptbox.model.enteties.AppCustomer;
import eu.net.ms.receiptbox.model.events.ReceiptAppIdUpdatedEvent;
import eu.net.ms.receiptbox.model.events.ReceiptCustomerInsertedEvent;
import eu.net.ms.receiptbox.model.events.ReceiptFetchEvent;
import eu.net.ms.receiptbox.model.events.ReceiptInsertedEvent;
import eu.net.ms.receiptbox.repositories.CustomerAppRepository;
import eu.net.ms.receiptbox.repositories.ReceiptRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by petsk on 2017-03-22.
 */

@Component
public class ReceiptCurrentStateHandler {
    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private CustomerAppRepository customerAppRepository;

    @Autowired
    private CommandGateway commandGateway;

    @EventHandler
    public void on(ReceiptInsertedEvent event) {
        receiptRepository.save(new Receipt(
                event.getReceiptId(),
                null,
                null,
                event.getReceiptData()));
    }

    @EventHandler
    public void on(ReceiptCustomerInsertedEvent event) {
        AppCustomer appCustomer = customerAppRepository.findOne(event.getCustomerId());
        if (appCustomer != null) {
            receiptRepository.save(new Receipt(
                    event.getReceiptId(),
                    event.getCustomerId(),
                    appCustomer.getAppId(),
                    event.getReceiptData()
            ));
            for (Receipt receipt : receiptRepository.findByAppId(appCustomer.getAppId())) {
                 if (receipt.getCustomerId() == null) {
                     receiptRepository.save(new Receipt(receipt.getReceiptId(), event.getCustomerId(), receipt.getAppId(), receipt.getReceiptData()));
                 }
            }
        } else {
            receiptRepository.save(new Receipt(
                    event.getReceiptId(),
                    event.getCustomerId(),
                    null,
                    event.getReceiptData()
            ));
        }
    }

    @EventHandler
    public void on(ReceiptAppIdUpdatedEvent event) {
        Receipt receipt = receiptRepository.findOne(event.getReceiptId());
        receiptRepository.save(new Receipt(event.getReceiptId(), receipt.getCustomerId(), event.getAppid(), receipt.getReceiptData()));
    }

    @EventHandler
    public void on(ReceiptFetchEvent event) {
        Receipt receipt = receiptRepository.findOne(event.getReceiptId());
        if (receipt.getAppId() == null) {
            commandGateway.send(new UpdateAppIdOnReceiptCommand(event.getReceiptId(), event.getAppId()));
        }
        List<AppCustomer> appCustomers = customerAppRepository.findByAppId(event.getAppId());
        if (appCustomers.size() == 0 && receipt.getCustomerId() != null) {
            AppCustomer c = new AppCustomer(receipt.getCustomerId(), event.getAppId());
            customerAppRepository.save(c);
            appCustomers.add(c);
        }
        for(AppCustomer appCustomer : appCustomers) {
            for (Receipt r : receiptRepository.findByCustomerId(appCustomer.getCustomerId())) {
                if (r.getAppId() == null) {
                    commandGateway.send(new UpdateAppIdOnReceiptCommand(r.getReceiptId(), event.getAppId()));
                }
            }
        }
    }
}
