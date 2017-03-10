package com.chathuribuddhi.dao;

import com.chathuribuddhi.util.WmeterProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public class ConnectionFactory {
    private static ConnectionFactory instance = new ConnectionFactory();

    public static ConnectionFactory getInstance() {
        return instance;
    }

    private ConnectionFactory() {
    }

    public Connection getConnection() throws SQLException {
        Properties properties = WmeterProperties.getInstance().getProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://"
                +properties.get("database.host")+":"
                +properties.get("database.port")+"/"
                +properties.get("database.name") ,
                properties.get("database.username").toString() ,
                properties.get("database.password").toString());
    }
}
