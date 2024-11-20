package ca.gbc.orderservice.stub;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class InventoryClientStub {

    public static void stubInventoryCall (String skuCode, Integer quantity){
        stubFor(get(urlEqualTo("/api/inventory?skuCode=" +skuCode+ "&quantity="+quantity))
                .willReturn(aResponse()
                        .withStatus(200)//the status code to expected from inventory service
                        .withHeader("Content-Type","application/json")
                        .withBody("true"))
        );
    }
}