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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public abstract class JacksonHouseParser implements HouseParser {
    private JsonFactory datatypeFactory;
    private URL input;

    protected JacksonHouseParser(URL input, JsonFactory datatypeFactory) {
        this.input = input;
        this.datatypeFactory = datatypeFactory;
    }

    public HouseEntry getHouseEntry() throws IOException, ParserException {
        ObjectMapper mapper = new ObjectMapper(datatypeFactory);
        JsonNode rootNode = mapper.readTree(this.input);

        if(rootNode == null) {
            throw new ParserException("invalid input format");
        }
        JsonNode houseNode = rootNode.get("house");
        if(houseNode == null || !houseNode.isObject()) {
            throw new ParserException("invalid input format");
        }

        return new JacksonHouseEntry(houseNode);
    }
}
