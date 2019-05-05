package com.ljj.crazyandbox.cnp;


import com.ljj.crazyandbox.cnp.droit.GrantedAuthority;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 认证模型
 */
public class DroitAuthentication extends AbstractDroitAuthentication {

    public DroitAuthentication(Client client, String topic, Map<String, String> param) {
        super(client, topic, param);
    }

    public DroitAuthentication(DroitRequest request){
        super(request.getClient(),request.getTopic(),request.getParam());

    }

    public String[] getDroits(){
        List<GrantedAuthority> grantedAuthority = getGrantedAuthority();
        if(grantedAuthority.size() == 0) return null;

        Iterator<GrantedAuthority> iterator = grantedAuthority.iterator();
        ArrayList<String> droits = new ArrayList<>();
        while (iterator.hasNext()){
            droits.add(iterator.next().getValue());
        }
        return  droits.toArray(new String[droits.size()]);
    }


}
