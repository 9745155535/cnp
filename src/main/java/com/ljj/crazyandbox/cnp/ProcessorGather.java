package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.processor.Processor;

import java.util.List;

public interface ProcessorGather {

    List<? extends Processor> getProcessors();

    void addProcessors(Processor processor);

    boolean isProcessorExist();
}
