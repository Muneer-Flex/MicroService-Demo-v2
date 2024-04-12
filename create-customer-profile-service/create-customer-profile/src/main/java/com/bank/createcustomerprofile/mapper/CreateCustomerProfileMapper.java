package com.bank.createcustomerprofile.mapper;

import com.bank.createcustomerprofile.entity.CustomerProfileEntity;
import com.bank.createcustomerprofile.model.CreateCustomerProfileRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Mapper
public interface CreateCustomerProfileMapper {

    CreateCustomerProfileMapper INSTANCE = Mappers.getMapper(CreateCustomerProfileMapper.class);

    @Mappings({
            @Mapping(source = "createCustomerProfileRequest.customerEmailId", target = "customerEmail"),
            @Mapping(source = "createCustomerProfileRequest.customerGoverningState", target = "governingState")
    })
    CustomerProfileEntity asCustomerProfileEntity(CreateCustomerProfileRequest createCustomerProfileRequest);
}
