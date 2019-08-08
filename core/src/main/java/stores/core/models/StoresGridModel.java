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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stores.core.beans.StoresBean;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Model(adaptables=Resource.class, resourceType = "stores/components/content/carouselgrid" , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json")
public class StoresGridModel {

    private Logger logger = LoggerFactory.getLogger(StoresGridModel.class);

    @Inject
    @Optional
    @JsonIgnore
    public Resource carouselGrids;

    @Inject @Default(intValues = 4)
    public int itemsPerSlide;

    @Inject
    public String[] carouselTitle;

    @Inject
    public String delay;

    @Inject
    public String autoplay;

    @JsonIgnore
    public Collection<List<StoresBean>> partitionedLists = new LinkedList<>();

    public List<StoresBean> storesBeanList = new LinkedList<>();

    @PostConstruct
    protected void init() {
        if(carouselGrids != null){
            Iterator<Resource> resources = carouselGrids.listChildren();
            while(resources.hasNext()){
                Resource resource = resources.next();
                ValueMap valueMp = resource.getValueMap();
                StoresBean storesBean = new StoresBean();
                storesBean.setCta(getValue(valueMp, "cta"));
                storesBean.setImagePath(getValue(valueMp, "imagePath"));
                storesBean.setPosition(getValue(valueMp, "position"));
                logger.info("storesBean::", storesBean.toString());
                storesBeanList.add(storesBean);
            }
            partitionedLists = partition(storesBeanList,itemsPerSlide);
            logger.info(partitionedLists.toString());
        }
    }
    private <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }

    private String getValue(ValueMap vMap, String s) {
        return vMap.containsKey(s) ? vMap.get(s).toString() : "";
    }

    public Collection<List<StoresBean>> getPartitionedLists() {
        return partitionedLists;
    }
}
