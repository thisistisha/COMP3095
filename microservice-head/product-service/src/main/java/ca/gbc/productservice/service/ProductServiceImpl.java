package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.model.Product;
import ca.gbc.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        log.debug("Creating a new product {}" , productRequest.name());  //log in dev env

        Product product = Product.builder()    //use builder   //in memory
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        //persist a product
        productRepository.save(product);// so as to save product in database via injecting repository

        log.info("Product {} is saved", product.getId());  // log in production env

        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice());

    }

    @Override
    public List<ProductResponse> getAllProducts() {

        log.debug("Returning a list of all products");

        List<Product> products = productRepository.findAll();

        //return products.stream().map(product -> mapToProductResponse(product)).toList();
        return products.stream().map(this::mapToProductResponse).toList(); //collection of products
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());

    }  //returning
    @Override
    public String updateProduct(String id, ProductRequest productRequest) {

        log.debug("Updating product with id {}" , id);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));   //where clause  id looking for id we pass in
        mongoTemplate.findOne(query, Product.class);  //find the product via product repository that matches above criteria
        // primary key incoming call matches one which is one of documents
        Product product = mongoTemplate.findOne(query, Product.class); //return statement

        if(product != null ) {
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price()); // in memory happens
            return productRepository.save(product).getId();  //communicating with database

        }
        return id;  //if not found anything return Product id
    }

    @Override
    public void deleteProduct(String id) {

        log.debug("Deleting product with id {}" , id);
        productRepository.deleteById(id);

    }
}
