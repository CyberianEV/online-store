package org.store.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.*;
import org.store.converters.ProductConverter;
import org.store.entities.Product;
import org.store.exceptions.ResourceNotFoundException;
import org.store.services.ProductService;
import org.store.soap.products.GetAllProductsRequest;
import org.store.soap.products.GetAllProductsResponse;
import org.store.soap.products.GetProductByIdRequest;
import org.store.soap.products.GetProductsByIdResponse;

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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        List<Product> products = new ArrayList<>(productService.findAll());
        products.forEach(p -> response.getProducts().add(productConverter.entityToSoap(p)));
        return response;
    }
}
