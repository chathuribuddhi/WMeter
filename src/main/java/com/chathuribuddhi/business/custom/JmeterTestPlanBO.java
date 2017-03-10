package com.chathuribuddhi.business.custom;

import com.chathuribuddhi.business.IBO;
import com.chathuribuddhi.dto.JmeterTestPlanDTO;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public interface JmeterTestPlanBO extends IBO<JmeterTestPlanDTO>{
    public JmeterTestPlanDTO startTest(String url, int userCount)throws Exception;
}
