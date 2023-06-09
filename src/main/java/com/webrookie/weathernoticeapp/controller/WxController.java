package com.webrookie.weathernoticeapp.controller;

import cn.hutool.json.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.webrookie.weathernoticeapp.message.TextMessage;
import com.webrookie.weathernoticeapp.models.dto.UserDTO;
import com.webrookie.weathernoticeapp.models.entity.User;
import com.webrookie.weathernoticeapp.models.utils.*;
import com.webrookie.weathernoticeapp.service.UserService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.List;

/**
 * @author WebRookie
 * @date 2023/3/31 17:42
 **/
@RequestMapping("/")
@RestController
@EnableAsync  // 异步的注解
public class WxController {

    @Resource
    private UserService userService;
    private static String TOKEN = "rookie";
    private static final SecureRandom random = new SecureRandom();
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
//        System.out.println(map);
        String message = "";
        try{
            System.out.println(map);
            String event = map.get("Event");
            System.out.println(event);
            if("subscribe".equals(event)) {
                System.out.println("subscribe");
                // 处理身份信息
                handleUserHasSubscribe(map.get("FromUserName"));
                message = getReplyMessage(map);
            }
            if("LOCATION".equals(event)) {
                System.out.println("LOCATION");
                handleGetLocation(map);
                sendModelMessage();
            }
            // 回复消息
        }catch (Exception e) {
            System.out.println("发送失败");
            e.printStackTrace();
        }

        return message;
    }

    /**
     * 处理订阅功能
     * @param fromUserName
     */
    private void handleUserHasSubscribe(String fromUserName) {
        UserDTO user = userService.getUser(fromUserName);
        if (user == null ) {
            // 用户不存在则记录下来
            System.out.println("no User");
            userService.createUser(fromUserName);
        }
    }

    /**
     * 更新用户在获取地址信息时
     * @param map
     */
    private void handleGetLocation(Map<String, String > map) {
        if(!"LOCATION".equals(map.get("Event"))) {
            return;
        }
        String latitude = map.get("Latitude");
        String longitude = map.get("Longitude");
        String openId = map.get("FromUserName");
        JSONObject cityInfo = Weather.getCityCode(longitude,latitude);
        UserDTO user = userService.getUser(openId);
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        System.out.println(cityInfo);
        user.setAdcode(cityInfo.getStr("adcode"));
        user.setCity(cityInfo.getStr("city"));
        userService.updateUser(user);
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
        textMessage.setContent("欢迎关注公众号,请允许获取您的地理位置");
        textMessage.setCreateTime(System.currentTimeMillis() / 1000);

        // XStream 将Java对象转换成xml字符串
        XStream xStream = new XStream();
        xStream.processAnnotations(TextMessage.class);
        String xml = xStream.toXML(textMessage);
        return xml;
    }



    /**
     * 推送模板信息消息
     * @return
     */
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendModelMessage() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", WxUtils.getAccessToken());
//        String requestParam = "{\n" +
//                "      \"touser\":\"oC4Nv50TEM4Kcc9V5LM-Co2rENHU\",\n" +
//                "      \"template_id\": + weatherTemplateId +,\n" +
//                "      \"data\":{\n" +
//                "        \"userName\":{\n" +
//                "         \"value\":\"宝贝儿～\"\n" +
//                "         \"color\":\"宝贝儿～\"\n" +
//                "         },\n" +
//                "       \"date\": {\n" +
//                "         \"value\":\"西安\"\n" +
//                "         },\n" +
//                "       \"weather\": {\n" +
//                "         \"value\":\"34.56\"\n" +
//                "         },\n" +
//                "       \"minTemperature\": {\n" +
//                "         \"value\":\"34.56\"\n" +
//                "         },\n" +
//                "       \"maxTemperature\": {\n" +
//                "         \"value\":\"34.56\"\n" +
//                "         },\n" +
//                "       \"wind\": {\n" +
//                "         \"value\":\"12\"\n" +
//                "         },\n" +
//                "       \"wet\": {\n" +
//                "         \"value\":\"24.56\"\n" +
//                "         },\n" +
//                "       \"birthDay\": {\n" +
//                "         \"value\":\"2\"\n" +
//                "         },\n" +
//                "       \"note\": {\n" +
//                "         \"value\":\"好好学习，天天向上\"\n" +
//                "         }\n" +
//                "     }\n" +
//                "}";
//        String requestResult = HttpMethod.httpJsonPost(url, requestParam);
//        System.out.println(requestResult);
        String openId = "oC4Nv57FolhCgVVLcgDoEhH3hZXo";
        String templateId = "j4cOFv5YXTQmjtslrlMvwLQkUK5FKEqp99VK4Mi1rRE";
        Map<String, Object> stringObjectMap = handleCreateModelMessage(openId);
        JSONObject  json = new JSONObject();
        json.accumulate("touser",openId);
        json.accumulate("template_id",templateId);
        json.set("data", stringObjectMap);
        System.out.println(json);
        String requestResult = HttpMethod.httpJsonPost(url, json.toString());
        System.out.println(requestResult);
    }

    /**
     * 根据订阅用户发送信息弹窗提示
     * @param openId
     * @return
     */
    public Map<String, Object> handleCreateModelMessage(String openId) {
        Map<String, Object> maps = new HashMap<String, Object>();
//        获取用户信息
        UserDTO user = userService.getUser(openId);
        //获取用户地址
        if(user == null || user.getAdcode() == null) {
            return null;
        }
        JSONObject todayWeather = Weather.getTodayWeather(user.getAdcode());
        System.out.println("以下是天气");
        System.out.println(todayWeather);
        String rainBowFart = RainBowFart.getRainBowFart();
        Map<String,Object> first = new HashMap<>();
        String defaultColor = "#000000";
        first.put("value", "宝贝");
        first.put("color", getColor(defaultColor));
        maps.put("userName", first);

        Map<String,Object> date = new HashMap<>();
        first.put("value", todayWeather.getStr("date"));
        first.put("color", getColor(defaultColor));
        maps.put("date", date);

        Map<String,Object> second = new HashMap<>();
        second.put("value",todayWeather.getStr("week"));
        second.put("color", getColor(defaultColor));
        maps.put("week", second);

        Map<String,Object> forth = new HashMap<>();
//        System.out.println(DateUtils.getNextChineseBirthDay(9,21));
        forth.put("value", DateUtils.getNextChineseBirthDay(9,21));
        forth.put("color", getColor(defaultColor));
        maps.put("yourBirthDay", forth);

        Map<String,Object> third = new HashMap<>();
        third.put("value", DateUtils.getNextChineseBirthDay(5,20));
        third.put("color", getColor(defaultColor));
        maps.put("myBirthDay", third);



        Map<String,Object> fifth = new HashMap<>();
        fifth.put("value", user.getCity());
        fifth.put("color", getColor(defaultColor));
        maps.put("city", fifth);

        Map<String,Object> weatherDay = new HashMap<>();
        weatherDay.put("value", todayWeather.getStr("dayweather"));
        weatherDay.put("color", getColor(defaultColor));
        maps.put("weatherDay", weatherDay);

        Map<String,Object> sixer = new HashMap<>();
        sixer.put("value", todayWeather.getStr("nightweather"));
        sixer.put("color", getColor(defaultColor));
        maps.put("weatherNight", sixer);

        Map<String,Object> temperatureDay = new HashMap<>();
        temperatureDay.put("value", todayWeather.getStr("daytemp"));
        temperatureDay.put("color", getColor(defaultColor));
        maps.put("temperatureDay", temperatureDay);

        Map<String,Object> temperatureNight = new HashMap<>();
        temperatureNight.put("value", todayWeather.getStr("nighttemp"));
        temperatureNight.put("color", getColor(defaultColor));
        maps.put("temperatureNight", temperatureNight);

        Map<String,Object> randomInfo = new HashMap<>();
        randomInfo.put("value", rainBowFart);
        randomInfo.put("color", getColor(defaultColor));
        maps.put("randomInfo", randomInfo);

        return maps;

    }

    /**
     * 随机颜色
     * @param color
     * @return
     */
    private static String getColor(String color) {
        if (ConfigConstant.randomMessageColorMode) {
            int i = random.nextInt(ConfigConstant.randomColors.length);
            return ConfigConstant.randomColors[i];
        } else {
            return color;
        }
    }}
