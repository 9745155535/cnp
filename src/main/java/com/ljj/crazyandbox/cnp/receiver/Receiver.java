package com.ljj.crazyandbox.cnp.receiver;


import com.ljj.crazyandbox.cnp.DroitGather;

/**
 * @author ljj
 * create time by 2019.3.29
 * des 接收器
 */
public interface Receiver {


     DroitGather receive(Object request);
}
