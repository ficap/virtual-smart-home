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

package com.redhat.patriot.smart_home_virtual.house;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public final class House {
    /*
    has:
        lights
        tv
        windows
        temperature
        ac
     */
    private Map<String, Device> devices;

    private static Map<String, House> houses = new ConcurrentHashMap<>();

    private House(Map<String, Device> devices) {
        this.devices = devices;

    }

    public static House getHouseInstanceFromURL(URL path) throws IOException {
        if(!houses.containsKey(path.toString())) {
            houses.put(path.toString(), new House.Parser(path).parseHouseFromConfig());
        }

        return houses.get(path.toString());
    }

    public Device getDeviceWithId(String id) {
        return devices.get(id);
    }

    public <T> T getDeviceWithId(String id, Class<T> type) {
        Device dev = getDeviceWithId(id);
        if (type.isInstance(dev)) {
            return type.cast(dev);
        }

        return null;  // or throw exception?
    }

    public <T> List<T> getAllDevices(Class<T> type) {
        ArrayList<T> toReturn = new ArrayList<>();

        for(Device d : devices.values()) {
            if (type.isInstance(d)) {
                toReturn.add(type.cast(d));
            }
        }
        return toReturn;
    }

    public static final class Parser {
        private URL input;

        private Parser(URL url) {
            this.input = url;
        }

        public House parseHouseFromConfig() throws IOException, IllegalArgumentException {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            JsonNode root = mapper.readTree(this.input);
            JsonNode houseNode = root.get("house");
            if(houseNode == null) {
                throw new ParserException("bad file format");
            }

            JsonNode devices = houseNode.get("devices");
            if(devices == null || !devices.isArray()) {
                throw new ParserException("bad file format");
            }

            House.Builder houseBuilder = new House.Builder();
            for(JsonNode device : devices) {
                if(!device.has("name") || !device.has("type")) {
                    throw new ParserException("bad file format");
                }

                JsonNode nameNode = device.get("name");
                JsonNode typeNode = device.get("type");

                if(!nameNode.isValueNode() || !typeNode.isValueNode()) {
                    throw new ParserException("bad file format");
                }

                String name = device.get("name").asText();
                String type = device.get("type").asText();
                int pcs = 1;
                JsonNode pcsNode = device.get("pcs");
                if(pcsNode != null && pcsNode.isInt() && pcsNode.asInt() > -1) {
                    pcs = pcsNode.asInt();
                }

                for(int i = 0; i < pcs; i++) {
                    houseBuilder.withDevice(name, type);
                }
            }

            return houseBuilder.createHouse();
        }

        public final class ParserException extends IOException {
            private ParserException(String message) {
                super(message);
            }
        }
    }

    public static class Builder {

        private Map<String, Device> devices = new ConcurrentHashMap<>();

        public Builder withDevices(List<Device> devices) {
            for(Device d : devices) {
                this.devices.put(d.getLabel(), d);
            }
            return this;
        }

        public Builder withDevice(Device device) {
            this.devices.put(device.getLabel(), device);
            return this;
        }

        public Builder withDevice(String name, String type, Object... args) throws IllegalArgumentException {
            this.devices.put(name, Device.createDevice(type, name, args));
            return this;
        }

        public House createHouse() {
            return new House(devices);
        }
    }


}
