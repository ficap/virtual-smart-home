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

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public class JacksonDeviceEntry implements DeviceEntry {
    private JsonNode node;

    public JacksonDeviceEntry(JsonNode node) {
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
    public String getType() throws ParserException {
        JsonNode type = node.get("type");
        if(type == null || !type.isValueNode()) {
            throw new ParserException("invalid input format");
        }
        return type.asText();
    }

    @Nullable
    @Override
    public Integer getPcs() throws ParserException {
        JsonNode pcs = node.get("pcs");
        if(pcs == null) {
            return null;
        }

        if(!pcs.isValueNode() || !pcs.canConvertToInt() || pcs.asInt() < 0) {
            throw new ParserException("invalid input format");
        }
        return pcs.asInt();
    }

    @Nullable
    @Override
    public String getUnit() throws ParserException {
        JsonNode unit = node.get("unit");
        if (unit == null) {
            return null;
        }
        if (!unit.isValueNode()) {
            throw new ParserException("invalid input format");
        }
        return unit.asText();
    }
}
