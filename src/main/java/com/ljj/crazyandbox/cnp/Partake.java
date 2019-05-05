package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.checker.Checker;
import com.ljj.crazyandbox.cnp.processor.Processor;

/**
 * @author ljj
 * create time by 2019.4.3
 */
public interface Partake {

    Checker[] getCheckers();

    Processor[] getProcessors();

    Checker[] effectiveCheckers();

    Processor[] effectiveProcessors();



    enum State {
        Disable,    // 无效
        Available   // 有效
    }
}
