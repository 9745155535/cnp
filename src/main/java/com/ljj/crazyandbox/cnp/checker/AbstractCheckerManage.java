package com.ljj.crazyandbox.cnp.checker;



import com.ljj.crazyandbox.cnp.*;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.processor.ProcessorManage;
import com.ljj.crazyandbox.cnp.provider.registry.DefinitionCheckerRegistrar;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author ljj
 * create time by 2019.4.2
 * 抽象检验管理者，校验赋予权限
 */
public abstract class AbstractCheckerManage implements CheckerManage {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DefinitionCheckerRegistrar.class);
    private final ProcessorManage processorManage;
    private final CheckerOperateService operateService;

    protected AbstractCheckerManage(ProcessorManage processorManage, CheckerOperateService operateService) {

        if (processorManage == null){
            throw new RuntimeException("not processorManage");
        }
        this.processorManage = processorManage;
        if(operateService == null) {
            throw new RuntimeException("not CheckerOperateService");

        }
        this.operateService = operateService;

        List<GrantedAuthority> collect = Arrays.asList(operateService.getCheckers()).stream().map(c -> c.getGrantedAuthority()).collect(Collectors.toList());
        String[] error = processorManage.checkEffectiveProcessor(collect.toArray(new GrantedAuthority[collect.size()]));
        if(StringUtility.allNotBlank(error)){
            throw new RuntimeException(String.join(",",error));
        }
    }

    @Override
    public DroitGather grant(DroitRequest request) {
        DroitAuthentication droitAuthentication;
        if (request instanceof DroitAuthentication) {
            droitAuthentication = (DroitAuthentication) request;
        } else {
            droitAuthentication = new DroitAuthentication(request);
        }
        droitAuthentication = grantDroit(droitAuthentication);
        if (droitAuthentication.isDroitExist()) {
            processorManage.execute(droitAuthentication);
        }
        return droitAuthentication;
    }


    protected DroitAuthentication grantDroit(DroitAuthentication droitAuthentication) {
        Checker[] checkers = getCheckers();
        StringBuilder stringBuilder = new StringBuilder();
        for (Checker checker : checkers) {
            try {
                if (checker.request(droitAuthentication)) {
                    droitAuthentication.addGrantedAuthority(checker.getGrantedAuthority());
                    stringBuilder.append(checker.getGrantedAuthority().getValue()+" ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("【"+this.getClass().getSimpleName()+"】" +" grant:"+ stringBuilder.toString());



        return droitAuthentication;
    }

    protected Checker[] getCheckers() {
        return this.operateService.effectiveCheckers();
    }


}
