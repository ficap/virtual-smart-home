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
public class Thermometer<UNIT> extends Sensor implements SimpleValueSensor<Float, UNIT> {
    public static final String DEFAULT_UNIT = "°C";

    private float temp;
    private UNIT unit;

    public Thermometer(String label) {
        super(label);
    }

    public Thermometer(String label, UNIT unit) {
        super(label);
        this.unit = unit;
    }

    @Override
    public Float getValue() {
        return this.temp;
    }

    @Override
    public UNIT getUnit() {
        return this.unit;
    }
}
