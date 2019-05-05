package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.checker.Checker;
import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import com.ljj.crazyandbox.cnp.processor.Processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ljj
 * create time by 2019.4.4
 * cnp 参与者操作类
 */
public abstract class AbstractOperateService implements CheckerOperateService, ProcessorOperateService {

    private final Map<GrantedAuthority, Checker> checkerMap;

    private final Map<String, Processor> processorMap;

    private final RuleResolver ruleResolver;

    protected AbstractOperateService(Map<String, Checker> checkerMap, Map<String, Processor> processorMap, RuleResolver ruleResolver) {
        if (ruleResolver == null) {
            this.ruleResolver = new RelationResolver();
        } else {
            this.ruleResolver = ruleResolver;
        }
        if (checkerMap == null || checkerMap.size() == 0) {
            throw new RuntimeException("not checker");
        }
        HashMap<GrantedAuthority, Checker> map = new HashMap<>();

        for (Map.Entry<String, Checker> e : checkerMap.entrySet()) {
            map.put(e.getValue().getGrantedAuthority(), e.getValue());
        }
        this.checkerMap = map;
        if (processorMap == null || processorMap.size() == 0) {
            throw new RuntimeException("no processor");
        }
        this.processorMap = processorMap;
    }

    @Override
    public RuleResolver getRuleResolver() {
        return this.ruleResolver;
    }


    @Override
    public String[] consumer(GrantedAuthority grantedAuthority) {
        List<String> processorName = new ArrayList<>();
        for (Processor processor : getProcessors()) {
            GrantedAuthority[] grantedAuthorityByRuleString = getRuleResolver().getGrantedAuthorityByRuleString(processor.getRuleString());
            for (GrantedAuthority authority : grantedAuthorityByRuleString) {
                if (authority.equals(grantedAuthority)) processorName.add(processor.getName());
            }
        }
        return processorName.size() == 0 ? null : processorName.toArray(new String[processorName.size()]);
    }

    @Override
    public GrantedAuthority[] provider(String name) {
        Processor processor = processorMap.get(name);
        if (processor == null) return null;
        if (!StringUtility.allNotBlank(processor.getRuleString()))
            return checkerMap.keySet().toArray(new GrantedAuthority[checkerMap.size()]);
        return getRuleResolver().getGrantedAuthorityByRuleString(processor.getRuleString());
    }


    @Override
    public Checker[] getCheckers() {
        return checkerMap.values().toArray(new Checker[checkerMap.size()]);
    }

    @Override
    public Checker getChecker(GrantedAuthority grantedAuthority) {
        return checkerMap.get(grantedAuthority);
    }


    @Override
    public Processor[] getProcessors() {
        return processorMap.values().toArray(new Processor[processorMap.size()]);
    }
    @Override
    public Processor getProcessor(String name) {
        return processorMap.get(name);
    }
}
