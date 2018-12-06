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

import com.redhat.patriot.smart_home_virtual.house.parsing.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class BasicTest {
    private void assertIsInstanceOf(Object o, Class type) {
        if(!type.isInstance(o))
            Assertions.fail("Object: " + o.toString() + " is not " + type.getCanonicalName());
    }

    @Test
    void testHouseFromFile() throws IOException, ParserException {
        House h = House.getHouseInstanceFromURL(BasicTest.class.getClassLoader()
                .getResource("house.yaml"));

        Device ac = h.getDeviceWithId("air-conditioner");
        Device tv = h.getDeviceWithId("television");
        List leds = h.getAllDevices(RGBLight.class);

        // ...
        Assertions.assertNotNull(ac);
        Assertions.assertNotNull(tv);
        Assertions.assertEquals(19, leds.size());
        assertIsInstanceOf(ac, Ac.class);
        assertIsInstanceOf(tv, Tv.class);
    }

    @Test
    void testRGBLight() throws IOException, ParserException {
        House h = House.getHouseInstanceFromURL(BasicTest.class.getClassLoader()
                .getResource("house.yaml"));

        RGBLight led = h.getDeviceWithId("colorful-light-1", RGBLight.class);
        led.setRed(100);
        led.setGreen(100);
        led.setBlue(50);

        Assertions.assertEquals(100, led.getRed());
        Assertions.assertEquals(100, led.getGreen());
        Assertions.assertEquals(50, led.getBlue());

        Assertions.assertFalse(led.isEnabled());
        led.switchIt(true);
        Assertions.assertTrue(led.isEnabled());
    }

    @Test
    void testHouseBean() throws IOException, ParserException {
        HouseBean bean = HouseBean.getInstance();
        House house = bean.getHouse();

        bean.acOn();
        Assertions.assertTrue(house.getDeviceWithId("air-conditioner", Ac.class).isEnabled());

        bean.configureRGBLight("2;r;20;g;30;b;50");

        RGBLight led = house.getDeviceWithId("colorful-light-2", RGBLight.class);

        Assertions.assertEquals(20, led.getRed());
        Assertions.assertEquals(30, led.getGreen());
        Assertions.assertEquals(50, led.getBlue());

    }

    @Test
    void testDeviceUnitPresent() throws IOException, ParserException {
        House h = House.getHouseInstanceFromURL(BasicTest.class.getClassLoader()
                .getResource("house.yaml"));
        Thermometer<String> t = h.getDeviceWithId("temp", Thermometer.class);
        Assertions.assertEquals("°F", t.getUnit());
    }

    @Test
    void testDeviceUnitDefault() throws IOException, ParserException {
        House h = House.getHouseInstanceFromURL(BasicTest.class.getClassLoader()
                .getResource("house.yaml"));
        Thermometer<String> t = h.getDeviceWithId("default-temp", Thermometer.class);
        Assertions.assertEquals("°C", t.getUnit());
    }

    @Test
    void testDataGenerator() throws IOException, ParserException {
        House h = House.getHouseInstanceFromURL(BasicTest.class.getClassLoader()
                .getResource("house.yaml"));
        Thermometer<String> thermometer = h.getDeviceWithId("default-temp", Thermometer.class);
        Hygrometer<String> hygrometer = h .getDeviceWithId("humidity", Hygrometer.class);

        Assertions.assertDoesNotThrow(() -> thermometer.getValue());
        Assertions.assertDoesNotThrow(() -> hygrometer.getValue());
    }
}
