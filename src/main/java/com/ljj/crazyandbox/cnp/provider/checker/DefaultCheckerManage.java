package com.ljj.crazyandbox.cnp.provider.checker;

import com.ljj.crazyandbox.cnp.CheckerOperateService;
import com.ljj.crazyandbox.cnp.checker.AbstractCheckerManage;
import com.ljj.crazyandbox.cnp.processor.ProcessorManage;


import java.util.Map;

/**
 * @author ljj
 * create time by 2019.4.2
 * des 默认校验者管理器
 */
public class DefaultCheckerManage extends AbstractCheckerManage {


    protected DefaultCheckerManage(ProcessorManage processorManage, CheckerOperateService operateService) {
        super(processorManage, operateService);
    }
}
