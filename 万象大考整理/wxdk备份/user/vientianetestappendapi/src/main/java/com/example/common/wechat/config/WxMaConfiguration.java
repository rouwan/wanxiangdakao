package com.example.common.wechat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * wechat mp configuration
 *
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@ConditionalOnClass(WxMaService.class)
@EnableConfigurationProperties(WxMaProperties.class)
public class WxMaConfiguration {

  @Autowired
  private WxMaProperties properties;


  @Bean
  @ConditionalOnMissingBean
  public WxMaConfig maConfig() {
    WxMaInMemoryConfig config = new WxMaInMemoryConfig();
    config.setAppid(this.properties.getAppid());
    config.setSecret(this.properties.getSecret());
    config.setToken(this.properties.getToken());
    config.setAesKey(this.properties.getAesKey());
    config.setMsgDataFormat(this.properties.getMsgDataFormat());

    return config;
  }

  @Bean
  @ConditionalOnMissingBean
  public WxMaService wxMaService(WxMaConfig maConfig) {
    WxMaService service = new WxMaServiceImpl();
    service.setWxMaConfig(maConfig);
    return service;
  }

//  @Bean
//  //@ConditionalOnMissingBean
//  public WxMpConfigStorage configStorage() {
//    WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
//    configStorage.setAppId(this.properties.getAppId());
//    configStorage.setSecret(this.properties.getSecret());
//    configStorage.setToken(this.properties.getToken());
//    configStorage.setAesKey(this.properties.getAesKey());
//    return configStorage;
//  }
//
//  @Bean
//  //@ConditionalOnMissingBean
//  public WxMpService wxMpService(WxMpConfigStorage configStorage) {
////        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.okhttp.WxMpServiceImpl();
////        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.jodd.WxMpServiceImpl();
////        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.apache.WxMpServiceImpl();
//    WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.WxMpServiceImpl();
//    wxMpService.setWxMpConfigStorage(configStorage);
//    return wxMpService;
//  }

}
