package com.jianxun.ram;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.ons.model.v20170918.OnsRegionListRequest;
import com.aliyuncs.ons.model.v20170918.OnsRegionListResponse;
import com.aliyuncs.ons.model.v20170918.OnsTopicListRequest;
import com.aliyuncs.ons.model.v20170918.OnsTopicListResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MQManagement {
    private DefaultAcsClient client;

    @Before
    public void init() {
        client = AcsClientFactory.getMQAcsClient();
    }

    @Test
    public void getONSRegionList() {
        OnsRegionListRequest request = new OnsRegionListRequest();
        request.setAcceptFormat(FormatType.JSON);
        request.setPreventCache(System.currentTimeMillis());
        OnsRegionListResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<OnsRegionListResponse.RegionDo> regionDoList = response.getData();
        for (OnsRegionListResponse.RegionDo regionDo : regionDoList) {
            System.out.println(regionDo.getId() + "  " +
                    regionDo.getOnsRegionId() + "  " +
                    regionDo.getRegionName() + "  " +
                    regionDo.getChannelId() + "  " +
                    regionDo.getChannelName() + "  " +
                    regionDo.getCreateTime() + "  " +
                    regionDo.getUpdateTime());
        }
    }

    @Test
    public void getTopicList() {
        String region = "cn-qingdao-publictest";
        OnsTopicListRequest request = new OnsTopicListRequest();
        /**
         *ONSRegionId 是指你需要 API 访问 MQ 哪个区域的资源.
         *该值必须要根据 OnsRegionList 方法获取的列表来选择和配置，因为 OnsRegionId 是变动的，不能够写固定值
         */
        request.setOnsRegionId(region);
        request.setPreventCache(System.currentTimeMillis());
        OnsTopicListResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        List<OnsTopicListResponse.PublishInfoDo> publishInfoDoList = response.getData();
        for (OnsTopicListResponse.PublishInfoDo publishInfoDo : publishInfoDoList) {
            System.out.println(publishInfoDo.getTopic() + "     " + publishInfoDo.getOwner());
        }
    }
}
