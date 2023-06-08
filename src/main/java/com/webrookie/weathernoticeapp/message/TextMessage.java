package com.webrookie.weathernoticeapp.message;

/**
 * @author WebRookie
 * @date 2023/6/8 16:21
 **/

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * <xml>
 *   <ToUserName><![CDATA[toUser]]></ToUserName>
 *   <FromUserName><![CDATA[fromUser]]></FromUserName>
 *   <CreateTime>12345678</CreateTime>
 *   <MsgType><![CDATA[text]]></MsgType>
 *   <Content><![CDATA[你好]]></Content>
 * </xml>
 * @author WebRookie
 */
@Data
@XStreamAlias("xml")
public class TextMessage {
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("CreateTime")
    private long createTime;
    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("Content")
    private String content;
}
