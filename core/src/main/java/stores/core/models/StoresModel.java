/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package stores.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.StoresBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Model(adaptables=Resource.class)
public class StoresModel {

    private Logger logger = LoggerFactory.getLogger(StoresModel.class);

    @Inject
    @Optional
    public Resource carousels;

    @Inject @Default(values = "5000")
    public String delay;

    public List<StoresBean> storesBeanList = new LinkedList<>();

    @PostConstruct
    protected void init() {
        if(carousels != null){
            Iterator<Resource> resources = carousels.listChildren();
            while(resources.hasNext()){
                Resource resource = resources.next();
                ValueMap valueMp = resource.getValueMap();
                StoresBean storesBean = new StoresBean();
                storesBean.setCta(getValue(valueMp, "cta"));
                storesBean.setImagePath(getValue(valueMp, "imagePath"));
                storesBean.setPosition(getValue(valueMp, "position"));
                storesBean.setDelay(delay);
                logger.info("storesBean::", storesBean.toString());
                storesBeanList.add(storesBean);
            }
        }
    }

    private String getValue(ValueMap vMap, String s) {
        return vMap.containsKey(s) ? vMap.get(s).toString() : "";
    }
    public List<StoresBean>  getStoresBeanList() {
        return storesBeanList;
    }
}
