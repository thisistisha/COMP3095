package ca.gbc.productservice.controller;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //mark this calss as a rest controller,
// allowing it to handle http request and respond with json or xml
@RequestMapping("/api/product")  //base url for all the endpoints in this controller.
//requests to /api/product will be mapped here
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping          // Maps HTTP POST requests to this method. Typically used for creating new resources. new
    @ResponseStatus(HttpStatus.CREATED) //// Maps HTTP GET requests to this method. Typically used to retrieve resources. new.
    public void createProduct(@RequestBody ProductRequest productRequest) { //call the createProduct,    productRequest in the requestBody
    //The data is sent in the body of the request, so it’s not visible in the URL.   //dto post request  with the payload of product request
        productService.createProduct(productRequest);
    }

    @GetMapping                 //// Maps HTTP GET requests to this method. Typically used to retrieve resources. new.
    @ResponseStatus(HttpStatus.OK) //// Sets the response status to 200 OK when the products are successfully retrieved
    public List<ProductResponse> getAllProduct(){          //get request to retrieve all product list the product request
        return productService.getAllProducts();
    }

    //https://localhost:8080/api/product/jdgkhvtyhc
    @PutMapping("/{productId}")  //// Maps HTTP PUT requests to this method.
    // The (productId) is a path variable used to identify which product to
    //@ResponseStatus(HttpStatus.NO_CONTENT)p //return statement
    public ResponseEntity<?> updateProduct(@PathVariable("productId") String productId,
                                           @RequestBody ProductRequest productRequest) {

        String updateProductId = productService.updateProduct(productId, productRequest);

        //set the location header attributes
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/product" + updateProductId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);  //other way //when cases

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId) {
     //ResponseEntity<?> is used when you want to return an HTTP response without specifying the type
        // of the response body, meaning that the resp public
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
