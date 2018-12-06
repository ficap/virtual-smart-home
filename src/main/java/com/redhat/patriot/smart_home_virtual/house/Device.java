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

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public abstract class Device {
    private final String label;

    public Device(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static Device createDevice(String type, String name) throws IllegalArgumentException {
        return performCreation(type, name, new Object[]{});
    }

    public static Device createDevice(String type, String name, Object... args) throws IllegalArgumentException {
        return performCreation(type, name, args);
    }

    private static Device performCreation(String type, String name, Object[] args) throws IllegalArgumentException {
        if("Ac".equalsIgnoreCase(type)) {
            return new Ac(name);

        } else if("Door".equalsIgnoreCase(type)) {
            return new Door(name);

        } else if("Fireplace".equalsIgnoreCase(type)) {
            return new Fireplace(name);

        } else if("RGBLight".equalsIgnoreCase(type)) {
            return new RGBLight(name);

        } else if("Tv".equalsIgnoreCase(type)) {
            return new Tv(name);

        } else if("Thermometer".equalsIgnoreCase(type)) {
            return new Thermometer(name, args.length > 0 && args[0] != null ? args[0].toString() : Thermometer.DEFAULT_UNIT);

        } else if("Hygrometer".equalsIgnoreCase(type)) {
            return new Hygrometer(name, args.length > 0 && args[0] != null ? args[0].toString() : Hygrometer.DEFAULT_UNIT);

        } else {
            throw new IllegalArgumentException("No such device type: " + type);
        }
    }

}
