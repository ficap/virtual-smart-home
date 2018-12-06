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

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public class Thermometer extends Sensor implements SimpleValueSensor<Float> {
    public static final String DEFAULT_UNIT = "°C";

    private DataFeed dataFeed = new NormalDistributionDataFeed(25, 1);
    private com.redhat.patriot.generator.device.Device device = new com.redhat.patriot.generator.device.Thermometer(getLabel(), dataFeed);

    public Thermometer(String label) {
        this(label, DEFAULT_UNIT);
    }

    public Thermometer(String label, String unit) {
        super(label);
        device.setUnit(unit);
    }

    @Override
    public Float getValue() {
        return (float) device.requestData();
    }

    @Override
    public String getUnit() {
        return device.getUnit();
    }
}
