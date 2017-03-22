package eu.net.ms.receiptbox.model.handlers;

import eu.net.ms.receiptbox.model.enteties.Dashboard;
import eu.net.ms.receiptbox.model.events.ReceiptCustomerInsertedEvent;
import eu.net.ms.receiptbox.model.events.ReceiptFetchEvent;
import eu.net.ms.receiptbox.model.events.ReceiptInsertedEvent;
import eu.net.ms.receiptbox.repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by petsk on 2017-03-22.
 */

@Component
public class DashboardHandler {
    @Autowired
    private DashboardRepository dashboardRepository;

    @EventHandler
    public void on(ReceiptFetchEvent event) {
        Dashboard dashboard = dashboardRepository.findOne("1");
        if (dashboard == null) {
            dashboard = new Dashboard("1", 0, 0);
        }
        dashboard.increaseNumberOfQueriesBy(1);
        dashboardRepository.save(dashboard);
    }

    @EventHandler
    public void on(ReceiptInsertedEvent event) {
        Dashboard dashboard = dashboardRepository.findOne("1");
        if (dashboard == null) {
            dashboard = new Dashboard("1", 0, 0);
        }
        dashboard.increaseNumberOfReceiptsBy(1);
        dashboardRepository.save(dashboard);
    }

    @EventHandler
    public void on(ReceiptCustomerInsertedEvent event) {
        Dashboard dashboard = dashboardRepository.findOne("1");
        if (dashboard == null) {
            dashboard = new Dashboard("1", 0, 0);
        }
        dashboard.increaseNumberOfReceiptsBy(1);
        dashboardRepository.save(dashboard);
    }
}
