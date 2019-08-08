package stores.core.services;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.Comment;
import stores.core.beans.Product;
import stores.core.beans.Products;
import stores.core.beans.Review;

import java.text.SimpleDateFormat;
import java.util.*;

@Component(service = ProductsService.class, immediate = true, name = "Product Json Exporter Service")
public class ProductsServiceImpl implements ProductsService {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductsServiceImpl.class);
    private static String RESOURCE_PATH = "/content/stores/en/stores/jcr:content/root/responsivegrid/products/products";

    @Override
    public List<Product> getProducts(ResourceResolver resourceResolver) {
        Resource resource = resourceResolver.getResource(RESOURCE_PATH);
        List<Product> products = new LinkedList<>();
        if (resource != null) {
            Iterator<Resource> children = resource.listChildren();
            while (children.hasNext()) {
                Resource rs = children.next();
                LOGGER.info("res={}, path={}", rs.getName(), rs.getPath());
                products.add(getProduct(rs));
            }
        }

        Products prds = new Products();
        prds.setProducts(products);
        return products;
    }

    private Product getProduct(Resource resource) {
        Product product = new Product();
        ValueMap vMap = resource.getValueMap();

        product.setId(Integer.parseInt(getValue(vMap, "id")));
        product.setName(getValue(vMap, "name"));
        product.setTitle(getValue(vMap, "title"));
        product.setStars(Integer.parseInt(getValue(vMap, "stars")));
        product.setImgurl(getValue(vMap, "imgurl"));
        product.setNumOfReviews(Integer.parseInt(getValue(vMap, "noOfReviews")));
        product.setDescription(getValue(vMap, "description"));
        product.setPrice(Integer.parseInt(getValue(vMap, "price")));
        product.setDiscount(Integer.parseInt(getValue(vMap, "discount")));
        product.setColor(getValue(vMap, "color"));
        product.setDimensions(getValue(vMap, "dimensions"));
        product.setDisplay(getValue(vMap, "display"));
        product.setMemory(getValue(vMap, "memory"));
        product.setProcessor(getValue(vMap, "processor"));
        product.setBatterylife(getValue(vMap, "batteryLife"));
        product.setGraphics(getValue(vMap, "graphics"));
        product.setStorage5(getValue(vMap, "storage5"));
        product.setConnections(getValue(vMap, "connections"));
        product.setSecurity(getValue(vMap, "security"));
        product.setSoftware(getValue(vMap, "software"));
        product.setTechSpec(getValue(vMap, "techSpec"));
        product.setReviews(getReviews(resource));

        return product;
    }

    private List<Review> getReviews(Resource resource) {
        List<Review> reviews = new LinkedList<>();
        LOGGER.info("getReviews");
        if (resource.getChild("reviews") != null) {
            Iterator<Resource> children = resource.getChild("reviews").listChildren();
            Review review = null;
            while (children.hasNext()) {
                Resource rs = children.next();
                ValueMap vMap = rs.getValueMap();
                review = new Review();
                review.setTitle(getValue(vMap, "title"));
                review.setRating(getValue(vMap, "rating"));
                review.setRateOne(getValue(vMap, "rateOne"));
                review.setRateTwo(getValue(vMap, "rateTwo"));
                review.setRateThree(getValue(vMap, "rateThree"));
                review.setRateFour(getValue(vMap, "rateFour"));
                review.setRateFive(getValue(vMap, "rateFive"));
                review.setProgressOne(getValue(vMap, "progressOne"));
                review.setProgressTwo(getValue(vMap, "progressTwo"));
                review.setProgressThree(getValue(vMap, "progressThree"));
                review.setProgressFour(getValue(vMap, "ProgressFour"));
                review.setProgressFive(getValue(vMap, "progressFive"));
                review.setComments(getComments(rs));

                reviews.add(review);
            }
        }
        return reviews;
    }

    private List<Comment> getComments(Resource resource) {
        List<Comment> comments = new LinkedList<>();
        LOGGER.info("getComments");
        if (resource.getChild("comments") != null) {
            Comment comment = null;
            Iterator<Resource> children = resource.getChild("comments").listChildren();
            while (children.hasNext()) {
                Resource rs = children.next();
                ValueMap vMap = rs.getValueMap();
                comment = new Comment();
                comment.setRate(getValue(vMap, "rate"));
                comment.setName(getValue(vMap, "name"));
                comment.setProfileIcon(getValue(vMap, "profileIcon"));
                comment.setComment(getValue(vMap, "comment"));
                comment.setDescription(getValue(vMap, "description"));
                comment.setDate(getDate(vMap, "date"));
                comments.add(comment);
            }
        }
        return comments;
    }

    private String getValue(ValueMap vMap, String s) {
        return vMap.containsKey(s) ? vMap.get(s).toString() : "";
    }

    private String getDate(ValueMap vMap, String s) {
        String timeStamp = "";
        SimpleDateFormat sdf;
        if(vMap.containsKey(s)){
            sdf = new SimpleDateFormat("MM/dd/YYYY");
            GregorianCalendar calendar = vMap.get(s, GregorianCalendar.class);
            if(calendar != null){
                timeStamp = sdf.format(calendar.getTime());
            }
        }
        return timeStamp;
    }

}
