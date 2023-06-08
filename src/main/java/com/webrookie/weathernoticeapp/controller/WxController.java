package com.webrookie.weathernoticeapp.controller;

import com.thoughtworks.xstream.XStream;
import com.webrookie.weathernoticeapp.message.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author WebRookie
 * @date 2023/3/31 17:42
 **/
@RequestMapping("/")
@RestController
public class WxController {
    private static String TOKEN = "rookie";
    @GetMapping("/hello")
    public String hello() {
        System.out.println("in Come");
        return "hello World";
    }
    @GetMapping("/")
     public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        System.out.println("signature:" + signature);
        System.out.println("timestamp:" + timestamp);
        System.out.println("nonce:" + nonce);
        System.out.println("echostr:" + echostr);
        List<String> list = Arrays.asList(TOKEN, timestamp, nonce);
        // 排序
        Collections.sort(list);
        // 将三个参数拼接成一个字符串进行sha1加密
        StringBuilder stringBuilder = new StringBuilder();
        for(String s: list) {
            stringBuilder.append(s);
        }
        // 加密
        try {
            MessageDigest instance = MessageDigest.getInstance("sha1");
            // 使用sha1进行加密，获取bytes数组
            byte[] digest = instance.digest(stringBuilder.toString().getBytes());
            // 将字符数组转成16进制
            StringBuilder sum = new StringBuilder();
            System.out.println(sum);
            for(byte b : digest) {
                // 一个字符是8个字节，然后signature看上去像是16进制组合的字符，所以用8字节分别取高四位、低四位，组合表示
                //  取字符的高四位 (b >> 4) & 15   右移4位，然后取与运算
                sum.append(Integer.toHexString((b >> 4) & 15));
                // 取字符的低四位  b & 15 直接进行于运算
                sum.append(Integer.toHexString (b & 15));
            }
            System.out.println("signature:"+ signature);
            System.out.println("sum:" + sum);
            if(StringUtils.hasText(signature) && signature.equals(sum.toString())) {
                System.out.println(echostr);
                return echostr;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @PostMapping("/")
    public String receiveMessage(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
//        byte[] b = new byte[1024];
//        int len = 0;
//        while ((len = inputStream.read(b)) != -1) {
//          // 将字节数组中的一部分截取出来变成一个字符串 offset指的是偏移量或者索引 public String(byte[] bytes,int offset,int length)
//            System.out.println(new String(b, 0, len));
//        }

        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        // 读取request输入流，获取Document对象
        try {
            Document document = reader.read(inputStream);
            // 获得root节点
            Element root = document.getRootElement();
            // 获取所有的字节点
            List<Element> elements = root.elements();
            for (Element element: elements) {
                map.put(element.getName(), element.getStringValue());
            }
        } catch (DocumentException e) {
           e.printStackTrace();
        }
        System.out.println(map);
        // 回复消息

        String message = getReplyMessage(map);
        System.out.println(message);
        return message;
    }

    /**
     * 获得回复的消息内容
     * @param map
     * @return xml格式的字符串
     */
    private String getReplyMessage(Map<String, String> map) {
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(map.get("FromUserName"));
        textMessage.setFromUserName(map.get("ToUserName"));
        textMessage.setMsgType("text");
        textMessage.setContent("欢迎关注公众号");
        textMessage.setCreateTime(System.currentTimeMillis() / 1000);

        // XStream 将Java对象转换成xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(TextMessage.class);
        String xml = xStream.toXML(textMessage);
        return xml;
    }
}
