package org.store.core.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.*;
import org.store.core.converters.ProductConverter;
import org.store.core.entities.Product;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.services.ProductService;
import org.store.core.soap.products.GetAllProductsRequest;
import org.store.core.soap.products.GetAllProductsResponse;
import org.store.core.soap.products.GetProductByIdRequest;
import org.store.core.soap.products.GetProductsByIdResponse;

import java.util.ArrayList;
import java.util.List;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.cyberstore.org/spring/ws/products";
    private final ProductService productService;
    private final ProductConverter productConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductsByIdResponse getProductsByName(@RequestPayload GetProductByIdRequest request) {
        GetProductsByIdResponse response = new GetProductsByIdResponse();
        Product product = productService.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find product, id: " + request.getId()));
        response.setProduct(productConverter.entityToSoap(product));
        return response;
    }

//Need to implement changes to work with pagination
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
//    @ResponsePayload
//    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
//        GetAllProductsResponse response = new GetAllProductsResponse();
//        List<Product> products = new ArrayList<>(productService.findAll());
//        products.forEach(p -> response.getProducts().add(productConverter.entityToSoap(p)));
//        return response;
//    }
}
