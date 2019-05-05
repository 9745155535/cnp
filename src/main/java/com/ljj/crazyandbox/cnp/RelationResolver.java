package com.ljj.crazyandbox.cnp;

import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


/**
 * @author ljj
 * create time by 2019.4.2
 * des 关系解析器
 */
public class RelationResolver implements RuleResolver {


    @Override
    public boolean resolver(String ruleString, String[] checkString) {
        Stack<ResolveData> stack = new Stack<>();
        Stack<CharType> operateStack = new Stack<>();
        List<ResolveData> ruleCheck = ruleStringCheck(ruleString);
        for (ResolveData r : ruleCheck) {
            push(r, stack, operateStack, checkString);

        }
        boolean compute = compute(stack, operateStack, checkString);

        return compute;
    }

    @Override

    public boolean resolver(List<ResolveData> rule, String[] checkString) {
        Stack<ResolveData> stack = new Stack<>();
        Stack<CharType> operateStack = new Stack<>();
        for (ResolveData r : rule) {
            push(r, stack, operateStack, checkString);

        }
        boolean compute = compute(stack, operateStack, checkString);

        return compute;
    }

    @Override
    public GrantedAuthority[] getGrantedAuthorityByRuleString(String ruleString) {
        return getGrantedAuthorityByRule(ruleStringCheck(ruleString));
    }

    @Override
    public GrantedAuthority[] getGrantedAuthorityByRule(List<ResolveData> rule) {
        List<GrantedAuthority> collect = rule.stream().filter(re -> re.getType().equals(CharType.character)).map(re -> {
            String value = re.getValue();
            return new GrantedAuthority() {
                @Override
                public String getValue() {
                    return value;
                }
            };
        }).collect(Collectors.toList());
        if (collect == null || collect.size() == 0)
            throw new RuntimeException("no rule");
            return collect.toArray(new GrantedAuthority[collect.size()]);
    }


    private List<ResolveData> ruleStringCheck(String ruleString) {
        if(!StringUtility.allNotBlank(ruleString)) throw new RuntimeException("ruleString not blank");
        List<Character> charList = new ArrayList();
        for (Character character : StringUtils.trimAllWhitespace(ruleString).toCharArray()) {
            charList.add(character);
        }
        List<ResolveData> resolveDatas = new ArrayList<>();
        while (charList.size() > 0) {
            resolveDatas.add(analysisOneString(charList));
        }

        return ruleCheck(resolveDatas);
    }

    private List<ResolveData> ruleCheck(List<ResolveData> rule) {
        // 校验括号
        List<ResolveData> objects = new ArrayList<>();
        int leftBracketsCount = 0;
        int reghtBracketsCount = 0;

        for (ResolveData resolveData : rule) {

            if (resolveData.getType().equals(CharType.leftBrackets)) {
                leftBracketsCount++;
            } else if (resolveData.getType().equals(CharType.reghtBrackets)) {
                reghtBracketsCount++;
            } else if(!resolveData.getType().equals(CharType.mistake)) {
                objects.add(resolveData);
            }
        }
        if (leftBracketsCount != reghtBracketsCount) throw new RuntimeException("'(' or ') No match");
        if (objects.size() == 0 || !objects.get(0).getType().equals(CharType.character) || (objects.size() - 1) % 2 != 0)
            throw new RuntimeException("error rule,not rule");
        objects.remove(0);
        for (int i = 0; i < objects.size() / 2; i++) {
            if (objects.get(i * 2).getType().equals(CharType.character) || !objects.get(i * 2 + 1).getType().equals(CharType.character)) {
                throw new RuntimeException("error rule,please use the correct interval");
            }

        }
        //校验非运算
        Iterator<ResolveData> iterator = rule.iterator();
        while(iterator.hasNext()){
            ResolveData next = iterator.next();
            ResolveData next1 = null;
            if(next.getType().equals(CharType.mistake)){
                try {
                    next1 = iterator.next();
                    if(!(next1.getType().equals(CharType.character)||next1.getType().equals(CharType.leftBrackets)))
                        throw new Exception();
                }catch (Exception e){
                    throw new RuntimeException("'!' position error, '!' not before '"+(next1==null?"":next1.getValue())+"'");
                }
            }
        }
        return rule;
    }

    @Override
    public String[] errorRule(String ruleString, String[] effectiveRule) {
        List<ResolveData> resolveData = ruleStringCheck(ruleString);
        List<String> collect = resolveData.stream().filter(re -> re.getType().equals(CharType.character)).map(re -> re.getValue()).collect(Collectors.toList());
        ArrayList<String> errorRule = new ArrayList();
        for (String rule : collect) {
            boolean b = true;
            for (String effective : effectiveRule) {
                if (effective.equals(rule)) {
                    b = false;
                    break;
                }
            }
            if (b) errorRule.add(rule);
        }
        return errorRule.size() == 0 ? null : errorRule.toArray(new String[errorRule.size()]);
    }


    private void push(ResolveData data, Stack<ResolveData> stack, Stack<CharType> operateStack, String[] checkString) {

        if (data.getType().equals(CharType.reghtBrackets)) {
            compute(stack, operateStack, checkString);
        } else if (data.getType().equals(CharType.and) || data.getType().equals(CharType.or)) {
            operateStack.push(data.getType());

        } else {
            stack.push(data);
        }

    }

    private boolean compute(Stack<ResolveData> stack, Stack<CharType> operateStack, String[] checkString) {
        ResolveData currentData = null;
        if (!stack.isEmpty()) currentData = stack.pop();
        while (!stack.isEmpty()) {

            CharType operate = null;
            ResolveData data = stack.pop();
            boolean currRelation = relation(currentData, checkString);

            if (data.getType().equals(CharType.leftBrackets)) {
                stack.push(currentData);
                return currentData.getType().equals(CharType.RelationTrue)?true:false;
            }else if(data.getType().equals(CharType.mistake)){
                currentData.setType(currRelation ? CharType.RelationFalse: CharType.RelationTrue);

            }else {
                operate = operateStack.pop();
                boolean relation = relation(data, checkString);

                if(!stack.isEmpty()){
                    ResolveData keep = stack.pop();
                    if(keep.getType().equals(CharType.mistake)){
                        relation = !relation;
                    }else {
                        stack.push(keep);
                    }
                }

                if (operate.equals(CharType.and)) {
                    currentData.setType(((currRelation && relation) ? CharType.RelationTrue : CharType.RelationFalse));
                } else if (operate.equals(CharType.or)) {
                    currentData.setType(((currRelation || relation) ? CharType.RelationTrue : CharType.RelationFalse));
                } else {
                    currentData.setType(((currRelation == false) ? CharType.RelationTrue : CharType.RelationFalse));
                    stack.push(data);
                }
            }

        }


        if (currentData.getType().equals(CharType.character))
            currentData.setType(relation(currentData, checkString) ? CharType.RelationTrue : CharType.RelationFalse);
        return currentData.getType().equals(CharType.RelationTrue);

    }

    private boolean relation(ResolveData data, String[] checkString) {
        boolean b = false;
        if (data.getType().equals(CharType.RelationTrue)) b = true;
        else if (data.getType().equals(CharType.RelationFalse)) ;
        else {
            for (String s : checkString) {
                if (s.equals(data.getValue())) {
                    b = true;
                }
            }
        }

        return b;
    }


    private ResolveData analysisOneString(List<Character> charList) {
        StringBuilder stringBuilder = new StringBuilder();
        ResolveData resolveData = new ResolveData();
        boolean Continue = true;
        while (charList.size() > 0) {
            Character c = charList.get(0);
            if (!Continue) break;
            CharType charEnum = getCharEnum(c);
            if (charEnum.ordinal() < CharType.character.ordinal() && stringBuilder.length() > 0) break;
            stringBuilder.append(c);
            charList.remove(0);

            switch (charEnum) {
                case leftBrackets:
                    resolveData.setType(CharType.leftBrackets);
                    Continue = false;
                    break;
                case reghtBrackets:
                    resolveData.setType(CharType.reghtBrackets);
                    Continue = false;
                    break;
                case character:
                    resolveData.setType(CharType.character);
                    break;
                case mistake:
                    resolveData.setType(CharType.mistake);
                    if (charList.size() > 0) {
                        if (getCharEnum(charList.get(0)).equals(CharType.mistake)) {
                            throw new RuntimeException("'!!' error");
                        }
                    }
                    Continue = false;
                    break;
                case or:
                    resolveData.setType(CharType.or);
                    if (charList.size() > 0) {
                        if (getCharEnum(charList.get(0)).equals(CharType.or)) {
                            charList.remove(0);
                        }
                    }
                    Continue = false;
                    break;
                case and:
                    resolveData.setType(CharType.and);
                    if (charList.size() > 0) {
                        if (getCharEnum(charList.get(0)).equals(CharType.and)) {
                            charList.remove(0);
                        }
                    }
                    Continue = false;
                    break;
                default:
                    throw new RuntimeException(c + " Incorrect characters");
            }
        }

        resolveData.setValue(stringBuilder.toString());
        return resolveData;
    }


    private CharType getCharEnum(char c) {
        CharType charType = CharType.Unknown;
        if (c == '(') charType = CharType.leftBrackets;
        else if (c == ')') charType = CharType.reghtBrackets;
        else if (c == '|') charType = CharType.or;
        else if (c == '!') charType = CharType.mistake;
        else if (c == '&') charType = CharType.and;
        else if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            charType = CharType.character;
        return charType;
    }


    public static void main(String[] args) {
        RelationResolver relationResolver = new RelationResolver();
        String[] strings = {"a", "b", "dqw11"};
        boolean stackt = relationResolver.resolver("a", strings);
        System.out.println(stackt);

    }

}
