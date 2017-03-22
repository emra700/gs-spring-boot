package eu.net.ms.receiptbox;

import eu.net.ms.receiptbox.model.enteties.Dashboard;
import eu.net.ms.receiptbox.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by petsk on 2017-03-22.
 */

@RestController
public class DashboardController {
    @Autowired
    private DashboardRepository dashboardRepository;

    @GetMapping(value = "/dashboard", produces = {APPLICATION_JSON_VALUE})
    public Dashboard getDashboard() {
        return dashboardRepository.findOne("1");
    }
}
