package stores.core.services;

import org.apache.sling.api.resource.ResourceResolver;
import stores.core.beans.Product;
import stores.core.beans.Products;

import java.util.List;

public interface ProductsService {
    List<Product> getProducts(ResourceResolver resourceResolver);
}
