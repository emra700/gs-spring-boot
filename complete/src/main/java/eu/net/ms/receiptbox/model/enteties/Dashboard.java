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
public class Dashboard {
    @Id
    @Getter
    private String dashboardId;
    @Getter
    private int numberOfReceipts;
    @Getter
    private int numberOfQueries;

    public void increaseNumberOfReceiptsBy(int amount) {
        numberOfReceipts = numberOfQueries + amount;
    }
    public void increaseNumberOfQueriesBy(int amount) {
        numberOfQueries = numberOfQueries + amount;
    }
}
