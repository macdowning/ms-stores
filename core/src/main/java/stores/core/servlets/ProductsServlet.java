package stores.core.servlets;

import com.day.crx.packaging.JSONResponse;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.Product;
import stores.core.services.ProductsService;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.List;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

/**
 * AEM out of box Servlet exposed as OSGI service to get Grid Data URL -
 * localhost:4502/bin/getStoresGridPayload.json
 */
@Component(service = Servlet.class, property = {
            Constants.SERVICE_DESCRIPTION + "= Products Json Exporter Servlet",
            SLING_SERVLET_METHODS +"=GET",
            SLING_SERVLET_RESOURCE_TYPES +"=stores/components/structure/page",
            SLING_SERVLET_EXTENSIONS +"=json",
            SLING_SERVLET_SELECTORS +"=data"
        })
public class ProductsServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LoggerFactory.getLogger(ProductsServlet.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Reference
    private ProductsService productsService;


    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws IOException {

        resp.setContentType(JSONResponse.APPLICATION_JSON_UTF8);

        try {
            List<Product> products = productsService.getProducts(req.getResourceResolver());
            String jsonResonse = new GsonBuilder().setPrettyPrinting().create().toJson(products);
            resp.getWriter().write(jsonResonse);

        } catch (Exception e) {
            resp.getWriter().write("An error occurred::" + e.getMessage());
        }
    }
}