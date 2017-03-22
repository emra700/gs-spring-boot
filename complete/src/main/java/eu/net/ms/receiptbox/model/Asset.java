package eu.net.ms.receiptbox.model;

import lombok.*;

import java.util.*;

/**
 * Created by petsk on 2017-03-22.
 */

@NoArgsConstructor
public class Asset {
    @Getter
    private String id;
    @Getter
    private Map<String, String> attributes = new HashMap<>();
    @Getter
    private Map<String, String> keys = new HashMap<>();
}
