package stores.core.beans;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {

    private List<Product> products = null;
    private final static long serialVersionUID = 249270986301853597L;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
