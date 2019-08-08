package stores.core.servlets;

import com.day.crx.packaging.JSONResponse;
import com.google.gson.GsonBuilder;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.StoresBean;
import stores.core.beans.StoresGridResponseBean;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * AEM out of box Servlet exposed as OSGI service to get Grid Data URL -
 * localhost:4502/bin/getStoresGridPayload.json
 */
@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Stores Grid Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/getStoresGridPayload",
        "sling.servlet.extensions=" + ".json" })
public class StoresGridServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LoggerFactory.getLogger(StoresGridServlet.class);
    private static String RESOURCE_PATH = "/content/stores/en/stores/jcr:content/root/responsivegrid/carouselgrid";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType(JSONResponse.APPLICATION_JSON_UTF8);
        StoresGridResponseBean responseBean = new StoresGridResponseBean();
        List<StoresBean> storesBeanList = new LinkedList<>();

        try {
            final Resource gridResource = req.getResourceResolver().getResource(RESOURCE_PATH);

            if (gridResource != null) {
                ValueMap valueMp = gridResource.getValueMap();
                responseBean.setAutoplay(getValue(valueMp, "autoplay"));
                responseBean.setCarouselTitle(getValue(valueMp, "carouselTitle"));
                responseBean.setDelay(getArrayValue(valueMp, "delay"));
                responseBean.setItemsPerSlide(getArrayValue(valueMp, "itemsPerSlide"));

                Iterator<Resource> resources = gridResource.getChild("carouselGrids").listChildren();
                Resource childResource = null;
                StoresBean storesBean = null;

                while (resources.hasNext()) {
                    childResource = resources.next();
                    valueMp = childResource.getValueMap();
                    storesBean = new StoresBean();
                    storesBean.setCta(getValue(valueMp, "cta"));
                    storesBean.setImagePath(getValue(valueMp, "imagePath"));
                    storesBean.setPosition(getValue(valueMp, "position"));
                    LOGGER.info("storesBean::", storesBean.toString());
                    storesBeanList.add(storesBean);
                }
                responseBean.setGridData(storesBeanList);

                String jsonResonse = new GsonBuilder().setPrettyPrinting().create().toJson(responseBean);
                resp.getWriter().write(jsonResonse);
            } else {
                resp.getWriter().write("No Data Available");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            resp.getWriter().write("An error occurred::"+e.getMessage());
        }
    }

    private String getValue(ValueMap vMap, String s) {
        return vMap.containsKey(s) ? vMap.get(s).toString() : "";
    }

    private String getArrayValue(ValueMap vMap, String s) {
        return vMap.containsKey(s) ? Arrays.toString(vMap.get(s, String[].class)).toString() : "";
    }
}
