package com.chathuribuddhi.dao.custom.impl;

import com.chathuribuddhi.dao.custom.JmeterTestPlanDAO;
import com.chathuribuddhi.dto.JmeterTestPlanDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public class JmeterTestPlanDAOImpl implements JmeterTestPlanDAO {

    private Connection connection;

    public void setConnection(Connection connection) throws Exception {
        this.connection = connection;
    }

    public boolean add(JmeterTestPlanDTO jmeterTestPlanDTO) throws Exception {
        PreparedStatement pst = connection.prepareStatement("INSERT INTO jmeter_test_plan(url, usercount) VALUES(?,?)");
        pst.setObject(1, jmeterTestPlanDTO.getUrl());
        pst.setObject(2, jmeterTestPlanDTO.getUserCount());
        int result = pst.executeUpdate();
        return (result > 0);
    }

    public boolean update(JmeterTestPlanDTO jmeterTestPlanDTO) throws Exception {
        PreparedStatement pst = connection.prepareStatement("UPDATE jmeter_test_plan SET url=?, usercount=? where id=?");
        pst.setObject(1, jmeterTestPlanDTO.getUrl());
        pst.setObject(2, jmeterTestPlanDTO.getUserCount());
        pst.setObject(3, jmeterTestPlanDTO.getId());
        int result = pst.executeUpdate();
        return (result > 0);
    }

    public boolean delete(String id) throws Exception {
        PreparedStatement pst = connection.prepareStatement("DELETE FROM jmeter_test_plan WHERE id=" + id);
        int result = pst.executeUpdate();
        return (result > 0);
    }

    public JmeterTestPlanDTO get(String id) throws Exception {
        String sql = "SELECT * FROM jmeter_test_plan WHERE id = " + id;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            return new JmeterTestPlanDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            );
        }
        return new JmeterTestPlanDTO();
    }

    public ArrayList<JmeterTestPlanDTO> getAll() throws Exception {
        ArrayList<JmeterTestPlanDTO> allJmeterTestPlanDTOs = new ArrayList<JmeterTestPlanDTO>();
        String sql = "SELECT * FROM jmeter_test_plan";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String url = resultSet.getString("url");
            int userCount = resultSet.getInt("usercount");
            JmeterTestPlanDTO jmeterTestPlanDTO = new JmeterTestPlanDTO(id, url, userCount);
            allJmeterTestPlanDTOs.add(jmeterTestPlanDTO);
        }
        return allJmeterTestPlanDTOs;
    }
}
