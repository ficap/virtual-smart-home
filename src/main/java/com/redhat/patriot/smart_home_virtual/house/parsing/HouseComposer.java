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

package com.redhat.patriot.smart_home_virtual.house.parsing;

import com.redhat.patriot.smart_home_virtual.house.House;

import java.io.IOException;

public class HouseComposer {
    private HouseParser parser;

    public HouseComposer(HouseParser parser) {
        this.parser = parser;
    }

    public House getHouse() throws IOException, ParserException {
        House.Builder builder = new House.Builder();
        HouseEntry houseEntry = parser.getHouseEntry();

        builder.withHouseName(houseEntry.getName());

        for (DeviceEntry d : houseEntry.getDevices()) {
            Integer pcs = d.getPcs();
            if (pcs == null) {
                pcs = d.DEFAULT_PCS;
            }
            for (int i = 0; i < pcs; i++) {
                try {
                    if (pcs == 1) {
                        builder.withDevice(d.getName(), d.getType(), d.getUnit());
                    } else {
                        builder.withDevice(d.getName() + String.valueOf(i), d.getType(), d.getUnit());
                    }
                } catch (IllegalArgumentException e) {
                    throw new ParserException("invalid input format");
                }
            }
        }
        return builder.createHouse();
    }
}
