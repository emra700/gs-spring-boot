package eu.net.ms.receiptbox.model.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by petsk on 2017-03-22.
 */

@AllArgsConstructor
public class InsertReceiptCustomerCommand {
    @TargetAggregateIdentifier
    @Getter
    private String receiptId;
    @Getter
    private String customerId;
    @Getter
    private String receiptData;
}
