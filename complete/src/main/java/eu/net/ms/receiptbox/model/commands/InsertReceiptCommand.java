package eu.net.ms.receiptbox.model.commands;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by petsk on 2017-03-22.
 */

@AllArgsConstructor
public class InsertReceiptCommand {
    @TargetAggregateIdentifier
    @Getter
    private String receiptId;
    @Getter
    private String receiptData;
}
