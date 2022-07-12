package com.hong.springframework.beans;

/**
 * @author wanghong
 * @date 2022/7/12
 * @apiNote
 */
public class BeansException extends RuntimeException {

    public BeansException(String msg){super(msg);}

    public BeansException(String msg,Throwable cause){super(msg,cause);}
}
