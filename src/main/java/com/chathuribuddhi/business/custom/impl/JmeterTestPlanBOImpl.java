package com.chathuribuddhi.business.custom.impl;

import com.chathuribuddhi.business.custom.JmeterTestPlanBO;
import com.chathuribuddhi.dao.ConnectionFactory;
import com.chathuribuddhi.dao.custom.JmeterTestPlanDAO;
import com.chathuribuddhi.dao.custom.impl.JmeterTestPlanDAOImpl;
import com.chathuribuddhi.dto.JmeterTestPlanDTO;
import com.chathuribuddhi.util.WmeterProperties;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.Calculator;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public class JmeterTestPlanBOImpl implements JmeterTestPlanBO {

    JmeterTestPlanDAO jmeterTestPlanDAO;

    public JmeterTestPlanBOImpl(JmeterTestPlanDAO jmeterTestPlanDAO) {
        this.jmeterTestPlanDAO = jmeterTestPlanDAO;
    }

    public boolean add(JmeterTestPlanDTO jmeterTestPlanDTO) throws Exception {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        jmeterTestPlanDAO.setConnection(connection);
        boolean result = jmeterTestPlanDAO.add(jmeterTestPlanDTO);
        connection.close();
        return result;
    }

    public boolean update(JmeterTestPlanDTO jmeterTestPlanDTO) throws Exception {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        jmeterTestPlanDAO.setConnection(connection);
        boolean result = jmeterTestPlanDAO.update(jmeterTestPlanDTO);
        connection.close();
        return result;
    }

    public boolean delete(JmeterTestPlanDTO jmeterTestPlanDTO) throws Exception {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        jmeterTestPlanDAO.setConnection(connection);
        boolean result = jmeterTestPlanDAO.delete(jmeterTestPlanDTO.getId());
        connection.close();
        return result;
    }

    public JmeterTestPlanDTO get(String id) throws Exception {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        jmeterTestPlanDAO.setConnection(connection);
        JmeterTestPlanDTO jmeterTestPlanDTO = jmeterTestPlanDAO.get(id);
        connection.close();
        return jmeterTestPlanDTO;
    }

    public ArrayList<JmeterTestPlanDTO> getAll() throws Exception {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        jmeterTestPlanDAO.setConnection(connection);
        ArrayList<JmeterTestPlanDTO> jmeterTestPlanDTOs = jmeterTestPlanDAO.getAll();
        connection.close();
        return jmeterTestPlanDTOs;
    }

    public JmeterTestPlanDTO startTest(String urlStr, int userCount) throws Exception {

        // TODO: Insert test plan details to the DB and get back the JmeterTestPlanDTO with ID.

        Properties properties = WmeterProperties.getInstance().getProperties();

        File jmeterHomePath = new File(properties.getProperty("jmeter.home.path"));
        String jmeterReportPath = properties.getProperty("jmeter.reports.path");
        String slash = System.getProperty("file.separator");
        File jmeterProperties = new File(jmeterHomePath.getPath() + slash + "bin" + slash + "jmeter.properties");

        StandardJMeterEngine jmeter = new StandardJMeterEngine();

        JMeterUtils.setJMeterHome(jmeterHomePath.getPath());
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
        JMeterUtils.setProperty("log_file",jmeterReportPath+"jmeter.log");
        JMeterUtils.initLogging();
        JMeterUtils.initLocale();

        URL url = new URL(urlStr);

        HTTPSampler httpSampler = new HTTPSampler();
        httpSampler.setDomain(url.getHost());
        httpSampler.setPort(url.getPort());
        httpSampler.setPath(url.getPath());
        httpSampler.setMethod("GET");
        httpSampler.setName(url.toString());
        httpSampler.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSampler.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

        LoopController loopController = new LoopController();
        loopController.setLoops(1);
        loopController.setFirst(true);
        loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
        loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
        loopController.initialize();

        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setName("Sample Thread Group");
        threadGroup.setNumThreads(userCount);
        threadGroup.setRampUp(1);
        threadGroup.setSamplerController(loopController);
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

        TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

        HashTree testPlanTree = new HashTree();
        testPlanTree.add(testPlan);
        HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
        threadGroupHashTree.add(httpSampler);

        File jmxFile = new File(jmeterReportPath + "jmeter_test.jmx");

        SaveService.saveTree(testPlanTree, new FileOutputStream(jmxFile));

        Summariser summariser = null;
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        if (summariserName.length() > 0) {
            summariser = new Summariser(summariserName);
        }

        String csvFile = jmeterReportPath + "jmeter_test_report.csv";
        ResultCollector logger = new ResultCollector(summariser);
        logger.setFilename(csvFile);
        testPlanTree.add(testPlanTree.getArray()[0], logger);

        jmeter.configure(testPlanTree);
        jmeter.run();

        // TODO : Get the summary and write it to the DB table

        return null; //Return JmeterTestPlanDTO with ID.
    }

    public static void main(String[] args) throws Exception {
        JmeterTestPlanBOImpl jmeterTestPlanBO = new JmeterTestPlanBOImpl(new JmeterTestPlanDAOImpl());
        jmeterTestPlanBO.startTest("http://www.google.com",200);
    }
}
