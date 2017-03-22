package eu.net.ms.receiptbox.model.commands;

import lombok.*;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
 * Created by petsk on 2017-03-22.
 */

@AllArgsConstructor
public class UpdateAppIdOnReceiptCommand {
    @TargetAggregateIdentifier
    @Getter
    private String receiptId;
    @Getter
    private String appId;
}
