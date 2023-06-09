package com.webrookie.weathernoticeapp.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 *  <item>
 *  *  *       <Title><![CDATA[title1]]></Title>
 *  *  *       <Description><![CDATA[description1]]></Description>
 *  *  *       <PicUrl><![CDATA[picurl]]></PicUrl>
 *  *  *       <Url><![CDATA[url]]></Url>
 *  *  *     </item>
 * @author WebRookie
 * @date 2023/6/9 10:03
 **/
@XStreamAlias("item")
@Data
public class Article {
    @XStreamAlias("Title")
    private String title;
    @XStreamAlias("Description")
    private String description;
    @XStreamAlias("PicUrl")
    private String picUrl;
    @XStreamAlias("Url")
    private String url;
}
