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
import com.redhat.patriot.generator.dataFeed.LinearDataFeed;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;
import com.redhat.patriot.generator.device.Thermometer;
import com.redhat.patriot.generator.device.active.ActiveDevice;
import com.redhat.patriot.smart_home_virtual.house.parsing.ParserException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:cap.filip.devel@gmail.com">Filip Čáp</a>
 */
public final class HouseBean {
    private static final Logger LOGGER = LogManager.getLogger(HouseBean.class);
    private static HouseBean houseBean;
    private House house;


    private HouseBean() throws IOException, ParserException {
        String path = System.getProperty("houseCfg", null);
        if(path != null) {
            this.house = House.getHouseInstanceFromURL(new File(path).toURI().toURL());
        } else {
            this.house = House.getHouseInstanceFromURL(HouseBean.class.getClassLoader().getResource("house.yaml"));
        }


    }

    public static HouseBean getInstance() throws IOException, ParserException {
        if(houseBean == null) {
            houseBean = new HouseBean();
        }

        return houseBean;
    }

    public void acOn() {
        ((Ac)(house.getDeviceWithId("air-conditioner"))).switchIt(true);
    }

    public void acOff() {
        ((Ac)(house.getDeviceWithId("air-conditioner"))).switchIt(false);
    }

    public void fireplaceOn() {
        ((Fireplace)(house.getDeviceWithId("fireplace"))).fireUp();
    }

    public void fireplaceOff() {
        ((Fireplace)(house.getDeviceWithId("fireplace"))).extinguish();
    }

    public void doorOpen() {
        ((Door)(house.getDeviceWithId("front-door"))).openDoor();
    }

    public void doorClose() {
        ((Door)(house.getDeviceWithId("front-door"))).closeDoor();
    }

    public void windowOpen() {
        ((Door)(house.getDeviceWithId("rear-door"))).openDoor();
    }

    public void windowClose() {
        ((Door)(house.getDeviceWithId("rear-door"))).closeDoor();
    }

    public void objInfo(Exchange exchange) {
        Object object = null;
        if(exchange.getProperty("type").equals(Ac.class.getSimpleName())) {
            object = house.getDeviceWithId("air-conditioner", Ac.class);

        } else if(exchange.getProperty("type").equals(Door.class.getSimpleName())) {
            if(exchange.getProperty("flag").equals("window"))
                object = house.getDeviceWithId("rear-door", Door.class);
            else
                object = house.getDeviceWithId("front-door", Door.class);
        } else if(exchange.getProperty("type").equals(Fireplace.class.getSimpleName())) {
            object = house.getDeviceWithId("fireplace", Fireplace.class);
        } else if(exchange.getProperty("type").equals(RGBLight.class.getSimpleName())) {
            object = house.getAllDevices(RGBLight.class);
        } else if(exchange.getProperty("type").equals(Tv.class.getSimpleName())) {
            object = house.getDeviceWithId("television", Tv.class);
        } else if(exchange.getProperty("type").equals(Object.class.getSimpleName())) {
            object = house.getAllDevices(Object.class);
        }

        exchange.getOut().setBody(object);
    }

    public void configureRGBLight(String msg) {
        for(String line : msg.split("\n")) {
            String[] parts = line.split(";");
            List<RGBLight> leds = new ArrayList<>();

            if(parts[0].equals("0xFFFF")) {
                leds = house.getAllDevices(RGBLight.class);
            } else {
                leds.add((RGBLight) house.getDeviceWithId("colorful-light-"+parts[0]));
            }

            for(RGBLight led : leds) {
                for(int i = 1; i < parts.length-1; i++) {
                    switch (parts[i]) {
                        case "r":
                            led.setRed(Integer.parseInt(parts[++i]));
                            break;
                        case "g":
                            led.setGreen(Integer.parseInt(parts[++i]));
                            break;
                        case "b":
                            led.setBlue(Integer.parseInt(parts[++i]));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void configureTv(Message msg) {
        String media = msg.getHeader("sound", "none", String.class);
        house.getDeviceWithId("television", Tv.class).setChannel(media);
    }

    public String getTemp() {
        return String.valueOf(house.getDeviceWithId("temp", com.redhat.patriot.smart_home_virtual.house.Thermometer.class).getValue());
    }

    public House getHouse() {
        return this.house;
    }
}
