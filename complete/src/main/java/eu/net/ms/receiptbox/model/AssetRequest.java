package eu.net.ms.receiptbox.model;

import lombok.*;

/**
 * Created by petsk on 2017-03-22.
 */
@NoArgsConstructor
public class AssetRequest {
    @Getter
    private String referenceno;
    @Getter
    private Asset asset;
}
