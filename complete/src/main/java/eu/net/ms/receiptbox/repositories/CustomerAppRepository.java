package eu.net.ms.receiptbox.repositories;

import eu.net.ms.receiptbox.model.enteties.AppCustomer;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

/**
 * Created by petsk on 2017-03-22.
 */
public interface CustomerAppRepository extends CrudRepository<AppCustomer, String> {
    List<AppCustomer> findByAppId(String appId);
}
