package eu.net.ms.receiptbox.model.events;

import lombok.*;

/**
 * Created by petsk on 2017-03-22.
 */

@AllArgsConstructor
public class ReceiptFetchEvent {
    @Getter
    private String receiptId;
    @Getter
    private String appId;
}
