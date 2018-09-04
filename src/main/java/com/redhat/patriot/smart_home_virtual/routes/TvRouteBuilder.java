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
public class TvRouteBuilder extends IntelligentHomeRouteBuilder {
   @Override
   public void configure() throws Exception {
      from("direct:tv-reset")
            .setHeader("sound", simple("/root/reset.wav"))
            .bean(HouseBean.getInstance(), "configureTv")
            .setBody(simple("OK"));

      from("direct:tv-romantic")
            .setHeader("sound", simple("/root/romantic.wav"))
            .bean(HouseBean.getInstance(), "configureTv")
            .setBody(simple("OK"));

      from("direct:tv-news")
            .setHeader("sound", simple("/root/news.wav"))
            .bean(HouseBean.getInstance(), "configureTv")
            .setBody(simple("OK"));

      from("direct:tv-coffee")
            .setHeader("sound", simple("/root/coffee.wav"))
            .bean(HouseBean.getInstance(), "configureTv")
            .setBody(simple("OK"));

      from("direct:tv-off")
            .bean(HouseBean.getInstance(), "configureTv")
            .setBody(simple("OK"));

      from(restBaseUri() + "/tv/romantic?httpMethodRestrict=GET")
            .to("direct:tv-romantic");

      from(restBaseUri() + "/tv/news?httpMethodRestrict=GET")
            .to("direct:tv-news");

      from(restBaseUri() + "/tv/coffee?httpMethodRestrict=GET")
            .to("direct:tv-coffee");

      from(restBaseUri() + "/tv/off?httpMethodRestrict=GET")
            .to("direct:tv-off");
   }
}