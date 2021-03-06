/*
 * Copyright 2018 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.redhat.patriot.smart_home_virtual.routes;

import com.redhat.patriot.smart_home_virtual.house.Fireplace;
import com.redhat.patriot.smart_home_virtual.house.HouseBean;
import org.apache.camel.model.dataformat.JsonLibrary;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class FireplaceRouteBuilder extends IntelligentHomeRouteBuilder {
   @Override
   public void configure() throws Exception {
      HouseBean houseBean = HouseBean.getInstance();

      from(restBaseUri() + "/fireplace/on?httpMethodRestrict=GET")
            .to("direct:fireplace-on")
            .to("direct:fireplace");

      from(restBaseUri() + "/fireplace/off?httpMethodRestrict=GET")
            .to("direct:fireplace-off")
            .to("direct:fireplace");

       from(restBaseUri() + "/fireplace?httpMethodRestrict=GET")
               .to("direct:fireplace");

       from("direct:fireplace")
               .setProperty("type", simple(Fireplace.class.getSimpleName()))
               .bean(houseBean, "objInfo")
               .marshal().json(JsonLibrary.Jackson);

      from("direct:fireplace-on")
            .bean(houseBean, "fireplaceOn");

      from("direct:fireplace-off")
            .bean(houseBean, "fireplaceOff");
   }
}