package eu.net.ms.receiptbox;

import eu.net.ms.receiptbox.model.aggregates.Receipt;
import eu.net.ms.receiptbox.model.commands.GetReceiptCommand;
import eu.net.ms.receiptbox.model.commands.InsertReceiptCommand;
import eu.net.ms.receiptbox.model.commands.InsertReceiptCustomerCommand;
import eu.net.ms.receiptbox.model.enteties.AppCustomer;

import eu.net.ms.receiptbox.repositories.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by petsk on 2017-03-22.
 */

@RestController
public class ReceiptController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private CustomerAppRepository customerAppRepository;

    @GetMapping(value = "/receipts", produces = {APPLICATION_JSON_VALUE})
    public Iterable<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    @GetMapping(value = "/customers", produces = {APPLICATION_JSON_VALUE})
    public Iterable<AppCustomer> getAllCustomers() {
        return customerAppRepository.findAll();
    }

    @GetMapping(value = "/receipt/{receiptId}", produces = {APPLICATION_JSON_VALUE})
    public Receipt getReceipt(@PathVariable String receiptId) {
        Receipt receipt = receiptRepository.findOne(receiptId);
        return receipt;
    }

    @GetMapping(value = "/receipt/{receiptId}/{appId}", produces = {APPLICATION_JSON_VALUE})
    public Receipt getReceipt(
            @PathVariable String receiptId,
            @PathVariable String appId) {
        commandGateway.send(new GetReceiptCommand(receiptId, appId));
        return receiptRepository.findOne(receiptId);
    }

    @GetMapping(value = "/receipts/{appId}", produces = {APPLICATION_JSON_VALUE})
    public List<Receipt> getReceipts(@PathVariable String appId) {
        return receiptRepository.findByAppId(appId);
    }

    @PostMapping(value ="/asset", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public String insertReceipt(@RequestBody String assetRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(assetRequest);
            String receiptId = jsonNode.findPath("receiptid").asText();
            String customerId = jsonNode.findPath("customerid").asText();
            String receiptData = jsonNode.findPath("eReceipt").asText();
            if (receiptId != "" && customerId != "")
                commandGateway.send(new InsertReceiptCustomerCommand(receiptId, customerId, receiptData));
            if (receiptId != "" && customerId == "")
                commandGateway.send(new InsertReceiptCommand(receiptId, receiptData));
            return assetRequest;
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }
/*
    @PostMapping(value ="/asset", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public AssetRequest insertReceipt(@RequestBody AssetRequest assetRequest) {
        if (assetRequest.getAsset().getKeys().get("customerid") == null)
            commandGateway.send(new InsertReceiptCommand(
                    assetRequest.getAsset().getKeys().get("receiptid"),
                    assetRequest.getAsset().getAttributes().get("eReceipt")
            ));
        else
            commandGateway.send(new InsertReceiptCustomerCommand(
                    assetRequest.getAsset().getKeys().get("receiptid"),
                    assetRequest.getAsset().getKeys().get("customerid"),
                    assetRequest.getAsset().getAttributes().get("eReceipt")
            ));
        return assetRequest;
    }
*/
    @PostMapping(value = "/receipt/{receiptId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public int insertReceipt(
            @PathVariable String receiptId,
            @RequestBody String receiptData) {
        commandGateway.send(new InsertReceiptCommand(receiptId, receiptData));
        return 1;
    }

    @PostMapping(value = "/receipt/{receiptId}/{customerId}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public int insertReceipt(
            @PathVariable String receiptId,
            @PathVariable String customerId,
            @RequestBody String receiptData) {
        commandGateway.send(new InsertReceiptCustomerCommand(receiptId, customerId, receiptData));
        return 1;
    }
}
