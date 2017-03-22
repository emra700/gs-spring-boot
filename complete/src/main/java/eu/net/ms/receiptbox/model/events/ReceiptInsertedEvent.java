package eu.net.ms.receiptbox.model.events;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by petsk on 2017-03-22.
 */

@AllArgsConstructor
public class ReceiptInsertedEvent {
    @TargetAggregateIdentifier
    @Getter
    private String receiptId;
    @Getter
    private String receiptData;
}
