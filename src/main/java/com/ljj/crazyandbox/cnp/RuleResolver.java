package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

import java.util.List;

/**
 * @author ljj
 * create time by 2019.4.2
 * 抽象解析者
 */
public interface RuleResolver {

     boolean resolver(String ruleString, String[] checkString);

     boolean resolver(List<ResolveData> rule, String[] checkString);

     GrantedAuthority[] getGrantedAuthorityByRuleString(String ruleString);

     GrantedAuthority[] getGrantedAuthorityByRule(List<ResolveData> rule);

     String[] errorRule(String ruleString, String[] effectiveRule);
}
