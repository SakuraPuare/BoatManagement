package com.sakurapuare.boatmanagement.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakurapuare.boatmanagement.config.WechatConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WechatUtils {
    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
    private final WechatConfig wechatConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public String getOpenId(String code) {
        try {
            String url = CODE2SESSION_URL
                    .replace("{appid}", wechatConfig.getAppId())
                    .replace("{secret}", wechatConfig.getAppSecret())
                    .replace("{code}", code);

            String response = restTemplate.getForObject(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);

            if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
                throw new RuntimeException("获取openid失败: " + jsonNode.get("errmsg").asText());
            }

            return jsonNode.get("openid").asText();
        } catch (Exception e) {
            throw new RuntimeException("调用微信接口失败", e);
        }
    }
} 