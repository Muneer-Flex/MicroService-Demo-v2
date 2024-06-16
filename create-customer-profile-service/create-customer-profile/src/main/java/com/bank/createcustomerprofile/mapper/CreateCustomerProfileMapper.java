package com.bank.createcustomerprofile.mapper;

import com.bank.createcustomerprofile.entity.CustomerProfileEntity;
import com.bank.createcustomerprofile.model.CreateCustomerProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateCustomerProfileMapper {

    CreateCustomerProfileMapper INSTANCE = Mappers.getMapper(CreateCustomerProfileMapper.class);

    @Mappings({
            @Mapping(source = "createCustomerProfileRequest.customerEmailId", target = "customerEmail"),
            @Mapping(source = "createCustomerProfileRequest.customerGoverningState", target = "governingState")
    })
    CustomerProfileEntity asCustomerProfileEntity(CreateCustomerProfileRequest createCustomerProfileRequest);
}
