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

import com.redhat.patriot.smart_home_virtual.house.HouseBean;

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class ServoRouteBuilder extends IntelligentHomeRouteBuilder {
   @Override
   public void configure() throws Exception {
      HouseBean houseBean = HouseBean.getInstance();

//      from(restBaseUri() + "/servo/set?httpMethodRestrict=GET")
//            .to("direct:servo-set");
      from("direct:door-open").bean(houseBean, "doorOpen");
      from("direct:door-close").bean(houseBean, "doorClose");

      from("direct:window-open").bean(houseBean, "windowOpen");
      from("direct:window-close").bean(houseBean, "windowClose");

      from(restBaseUri() + "/door/open")
            .to("direct:door-open");

      from(restBaseUri() + "/door/close")
            .to("direct:door-close");

      from(restBaseUri() + "/window/open")
            .to("direct:window-open");

      from(restBaseUri() + "/window/close")
            .to("direct:window-close");
   }
}