package eu.net.ms.receiptbox.model.enteties;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by petsk on 2017-03-22.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AppCustomer {
    @Id
    @Getter
    private String customerId;
    @Getter
    private String appId;
}
