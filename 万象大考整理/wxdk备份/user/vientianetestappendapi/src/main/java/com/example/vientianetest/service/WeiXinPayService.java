package com.example.vientianetest.service;

import com.alibaba.fastjson.JSONObject;
import com.example.common.domain.BasicResultObject;
import com.example.common.domain.BasicTagState;
import com.example.common.domain.BasicUserInfo;
import com.example.common.wechat.utils.PayCommonUtil;
import com.example.vientianetest.redis.SessionRedis;
import org.apache.logging.log4j.LogManager;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

@Component
public class WeiXinPayService {
    @Autowired
    private SessionRedis sessionRedis;
    @Value("${wechat.miniapp.appid}")
    private String APPID;
    @Value("${wechat.miniapp.apikey}")
    private String APIKEY;
    @Value("${wechat.miniapp.mchid}")
    private String MCHID;
    @Value("${wechat.miniapp.requestUrl}")
    private String RequestUrl;

    org.apache.logging.log4j.Logger logger = LogManager.getLogger("RollingRandomAccessFileLogger");
    String timeMillis = String.valueOf(System.currentTimeMillis() / 1000);
    String randomString = PayCommonUtil.getRandomString(32);
    public static String wxnotify = "/toPaySucc";

    // region toPay
    public String toPay(HttpServletRequest request ,int subjectId, String totalAmount , String description) {
        BasicResultObject obj = new BasicResultObject();
        System.out.println("发起支付");
        try{
            BasicUserInfo userInfo = sessionRedis.getAttribute(request.getCookies());
            int userId = userInfo.getUserId();
            String openId =  userInfo.getOpenId(); // "orIO05LlRi9z6ZwZIBiXQ8EAeLuc";
            String sym = RequestUrl;
            String trade_no0 = userId + "O"+ subjectId +"O"+ UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
            String trade_no = trade_no0.substring(0,32);
            Map<String, String> map = weixinPrePay(trade_no ,new BigDecimal(totalAmount),description,openId,sym,request);
            SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
            finalpackage.put("appId", APPID);
            finalpackage.put("timeStamp", timeMillis);
            finalpackage.put("nonceStr", randomString);
            finalpackage.put("package", "prepay_id="+map.get("prepay_id"));
            finalpackage.put("signType", "MD5");
            String sign = PayCommonUtil.createSign("UTF-8", finalpackage,APIKEY);
            finalpackage.put("paySign", sign);
            obj.Result = finalpackage;
            System.out.println("发起支付：待支付");
        }
        catch(Exception ex){
            obj.Tag = BasicTagState.TagAbnormal;
            obj.Message = " fail ";
            logger.info("发起支付：失败。->toPay Exception: "+ex.getMessage());
            System.out.println("发起支付：失败");
        }

        return JSONObject.toJSONString(obj);
    }
    //endregion

    //region weixinPrePay

    public Map<String, String> weixinPrePay(String trade_no,BigDecimal totalAmount,
                                            String description, String openid,String sym, HttpServletRequest request) {
        SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();
        parameterMap.put("appid", APPID);
        parameterMap.put("mch_id", MCHID);
        parameterMap.put("nonce_str", randomString);
        parameterMap.put("body", description);
        parameterMap.put("out_trade_no", trade_no);
        parameterMap.put("fee_type", "CNY");
        System.out.println("jiner");
        BigDecimal total = totalAmount.multiply(new BigDecimal(100));
        java.text.DecimalFormat df=new java.text.DecimalFormat("0");
        parameterMap.put("total_fee", df.format(total));
        System.out.println("jiner2");
        //parameterMap.put("spbill_create_ip", request.getRemoteAddr());
        parameterMap.put("spbill_create_ip", "1.1.1.1"); // 测试改成1.1.1.1
        parameterMap.put("notify_url", sym + wxnotify); // 回调不配了
        parameterMap.put("trade_type", "JSAPI");
        //trade_type为JSAPI是 openid为必填项
        parameterMap.put("openid", openid);
        System.out.println("");
        String sign = PayCommonUtil.createSign("UTF-8", parameterMap,APIKEY);
        System.out.println("jiner2");
        parameterMap.put("sign", sign);
        String requestXML = PayCommonUtil.getRequestXml(parameterMap);
        System.out.println(requestXML);
        String result = PayCommonUtil.httpsRequest(
                "https://api.mch.weixin.qq.com/pay/unifiedorder", "POST",
                requestXML);
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = PayCommonUtil.doXMLParse(result);
        } catch (JDOMException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info(" weixinPrePay result = "+result);
        return map;
    }
    //endregion

    //region toPaySucc
    public void toPaySucc(HttpServletRequest request, HttpServletResponse response ){
        String result = "";
        String fail = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
        String success = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = null;
        System.out.println("微信支付回调");
        try{
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            String resultxml = new String(outSteam.toByteArray(), "utf-8");
            Map<String, String> params = PayCommonUtil.doXMLParse(resultxml);
            outSteam.close();
            inStream.close();
            if (!PayCommonUtil.isTenpaySign(params,APIKEY)) {
                // 支付失败
                result = fail;
            } else {
                System.out.println("===============付款成功==============");
                // ------------------------------
                // 处理业务开始
                // ------------------------------
                // 此处处理订单状态，结合自己的订单数据完成订单状态的更新
                // ------------------------------

                String total_fee = params.get("total_fee"); // 金额
                double v = Double.valueOf(total_fee) / 100;
                int userId = Integer.parseInt(params.get("out_trade_no").split("O")[0]);
                int subjectId = Integer.parseInt(params.get("out_trade_no").split("O")[1]);
                String transactionId = params.get("transaction_id"); /*交易单号*/
                String outTradeNo = params.get("out_trade_no"); /*商品单号*/
                if(subjectId>0){ // 跳过某一道题

                }else { // 补充体力

                }
                writer = response.getWriter();
                result = success;
            }
        }
        catch (Exception ex){
            result =  fail;
        }
        if(writer != null ){
            writer.write(result);
            writer.flush();
            writer.close();
        }
    }
    //endregion

}
