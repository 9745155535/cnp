package com.ljj.crazyandbox.cnp.processor;



import com.ljj.crazyandbox.cnp.DroitAuthentication;
import com.ljj.crazyandbox.cnp.DroitGather;
import com.ljj.crazyandbox.cnp.ProcessorOperateService;
import com.ljj.crazyandbox.cnp.StringUtility;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.provider.registry.DefinitionCheckerRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ljj
 * create time by 2019.4.2
 * des ljj
 */
public abstract class AbstractProcessorManage implements ProcessorManage {

    private static final Logger log = LoggerFactory.getLogger(DefinitionCheckerRegistrar.class);

    private final ProcessorOperateService operateService;

    protected AbstractProcessorManage(ProcessorOperateService operateService) {

        this.operateService = operateService;
    }

    @Override
    public DroitAuthentication execute(DroitAuthentication droitAuthentication) {
        List<Processor> processors = getProcessor();
        StringBuilder stringBuilder = new StringBuilder();

        for (Processor processor : processors) {
            if (isAdopt(processor.getRuleString(), droitAuthentication)) {
                try {
                    processor.handle(droitAuthentication);
                    droitAuthentication.addProcessors(processor);
                    stringBuilder.append(processor.getName()+" ");
                } catch (Exception e){
                    log.error(processor.getName()+" handle error");
                }
            }
        }
        log.info("【"+this.getClass().getSimpleName()+"】"+" execute: "+stringBuilder.toString());
        return droitAuthentication;
    }


    @Override
    public String[] checkEffectiveProcessor(GrantedAuthority[] effectiveDroit) {
        List<String> error = new ArrayList<>();
        Arrays.asList(operateService.getProcessors()).forEach((v) -> {
            List<String> collect = Arrays.asList(effectiveDroit).stream().map(droit -> droit.getValue()).collect(Collectors.toList());

            String[] strings = StringUtility.allNotBlank(v.getRuleString()) ? operateService.getRuleResolver().errorRule(v.getRuleString(), collect.toArray(new String[collect.size()])) : null;
            if (StringUtility.allNotBlank(strings))
                error.add(v.getName() + "rule error : " + String.join(",", strings) + " not existent");
        });

        return error.size() == 0 ? null : error.toArray(new String[error.size()]);
    }

    protected boolean isAdopt(String ruleString, DroitGather droitGather) {
        List<String> collect = droitGather.getGrantedAuthority().stream().map(droit -> ((GrantedAuthority) droit).getValue()).collect(Collectors.toList());
        String[] effective = collect.toArray(new String[collect.size()]);
        return StringUtility.allNotBlank(ruleString) ? operateService.getRuleResolver().resolver(ruleString, effective) : true;
    }


    protected List<Processor> getProcessor() {
        return Arrays.asList(operateService.effectiveProcessors());
    }

}
