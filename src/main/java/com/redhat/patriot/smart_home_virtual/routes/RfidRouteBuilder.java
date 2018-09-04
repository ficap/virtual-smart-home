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

/**
 * @author <a href="mailto:pavel.macik@gmail.com">Pavel Macík</a>
 */
public class RfidRouteBuilder extends IntelligentHomeRouteBuilder {
   @Override
   public void configure() throws Exception {
     // TODO: implement using data generator
//      from("timer:sensorBroadcast?period=500")
//            .bean(HouseBean.getInstance(), "")
//            .choice()
//               .when(body().isNotEqualTo("00000000"))
//               .marshal().json(JsonLibrary.Jackson, true)
//               .log("RFID tag: ${body}")
//               .to("websocket:rfidTags?sendToAll=true");
   }
}