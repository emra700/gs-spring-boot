package eu.net.ms.receiptbox.repositories;

import eu.net.ms.receiptbox.model.aggregates.Receipt;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

/**
 * Created by petsk on 2017-03-22.
 */
public interface ReceiptRepository extends CrudRepository<Receipt, String> {
    List<Receipt> findByAppId(String appid);
    List<Receipt> findByCustomerId(String customerId);
}
