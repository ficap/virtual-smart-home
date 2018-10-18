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

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public class JacksonHouseEntry implements HouseEntry {
    private JsonNode node;

    public JacksonHouseEntry(JsonNode node) {
        this.node = node;
    }

    @Override
    public String getName() throws ParserException {
        JsonNode name = node.get("name");
        if(name == null || !name.isValueNode()) {
            throw new ParserException("invalid input format");
        }
        return name.asText();
    }

    @Override
    public List<DeviceEntry> getDevices() throws ParserException {
        JsonNode devicesNode = node.get("devices");
        if(devicesNode == null || !devicesNode.isArray()) {
            throw new ParserException("invalid input format");
        }
        ArrayList<DeviceEntry> deviceEntries = new ArrayList<>();

        for(JsonNode deviceNode : devicesNode) {
            if(!deviceNode.isObject()) {
                throw new ParserException("invalid input format");
            }
            deviceEntries.add(new JacksonDeviceEntry(deviceNode));
        }
        return deviceEntries;
    }
}
