package com.exe01.backend.converter;

import com.exe01.backend.dto.request.BusinessRequest;
import com.exe01.backend.dto.response.business.BusinessResponse;
import com.exe01.backend.entity.Business;

public class BusinessConverter {
    public static BusinessResponse fromEntityToBusinessResponse(Business business) {
        BusinessResponse businessResponse = new BusinessResponse();
        businessResponse.setName(business.getName());
        businessResponse.setIndustry(business.getIndustry());
        businessResponse.setLocation(business.getLocation());
        businessResponse.setDescription(business.getDescription());
        businessResponse.setLogoPicture(business.getLogoPicture());
        businessResponse.setBackgroundPicture(business.getBackgroundPicture());
        businessResponse.setStatus(business.getStatus());

        return businessResponse;
    }

    public static Business fromRequestToEntity(BusinessRequest request) {
        Business business = new Business();
        business.setName(request.getName());
        business.setIndustry(request.getIndustry());
        business.setLocation(request.getLocation());
        business.setDescription(request.getDescription());
        business.setLogoPicture(request.getLogoPicture());
        business.setBackgroundPicture(request.getBackgroundPicture());
        business.setStatus(request.getStatus());

        return business;
    }
}
