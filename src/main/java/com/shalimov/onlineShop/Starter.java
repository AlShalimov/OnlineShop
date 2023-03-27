package com.shalimov.onlineShop;

import com.shalimov.onlineShop.util.PropertyReader;


import java.util.Properties;

public class Starter {
    PropertyReader propertyReader = new PropertyReader("application.properties");
    Properties properties = propertyReader.getProperties();


}
