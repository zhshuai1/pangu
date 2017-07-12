package com.sepism.pangu.external;

import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.exception.ExternalDependencyFailureException;
import com.sepism.pangu.model.handler.Response;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
// should do the I18, since for different area, the sms vendor is different.
public class SmsSender {
    private static final String url = "http://gw.api.taobao.com/router/rest";
    // should hide the appKey and secret from the code. This is about money.
    private static final String appkey = "23329407";
    private static final String secret = "c169a1fee24f0a712a27b8d25da460a1";
    private static final String freeSignName = "七棱镜";
    private static final Map<SmsUsage, String> templateUsageMap = new HashMap<SmsUsage, String>() {{
        put(SmsUsage.REGISTER, "SMS_76510126");
    }};

    private static final String type = "normal";

    private static final Gson GSON = new Gson();


    public Response sendSms(String phone, String code, SmsUsage usage) throws ExternalDependencyFailureException {
        /**
         * Alidayu api: https://api.alidayu.com/doc2/apiDetail?spm=a3142.7629140.1999205496.19.X84LjU&apiId=25450
         */
        AlibabaAliqinFcSmsNumSendResponse rsp = null;
        try {
            TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            String templateId = templateUsageMap.get(usage);
            req.setExtend(phone);
            req.setSmsType(type);
            req.setSmsFreeSignName(freeSignName);
            req.setSmsParamString("{code:'" + code + "'}");
            req.setRecNum(phone);
            req.setSmsTemplateCode(templateId);
            rsp = client.execute(req);
        } catch (ApiException e) {
            log.error("Failed to send sms to phone {}", phone, e);
            throw new ExternalDependencyFailureException("Failed to call Alidayu.", e);
        }
        if (rsp.isSuccess()) {
            log.info("Send sms to customer successfully.", GSON.toJson(rsp));
            return new Response(ErrorCode.SUCCESS);
        } else {
            log.warn("The response from ali is {}", GSON.toJson(rsp));
            return new Response(ErrorCode.INTERNAL_ERROR);
        }
    }

    public enum SmsUsage {
        REGISTER, FORGETPASSWORD, LOGIN;
    }
}
