package stores.core.servlets;

import com.day.crx.packaging.JSONResponse;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.Product;
import stores.core.beans.Products;
import stores.core.beans.StoresBean;
import stores.core.beans.StoresGridResponseBean;
import stores.core.services.ProductsService;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * AEM out of box Servlet exposed as OSGI service to get Grid Data URL -
 * localhost:4502/bin/getProducts.json
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Products Json Exporter Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/getProducts",
        "sling.servlet.extensions=" + ".json" })
public class ProductsServlet extends SlingAllMethodsServlet {

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
            resp.getWriter().write("An error occurred::"+e.getMessage());
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
