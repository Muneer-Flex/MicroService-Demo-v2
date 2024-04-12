package com.bank.governingstate.service;

import com.bank.governingstate.annotations.LogMethodTimeExecution;
import com.bank.governingstate.constants.ErrorConstants;
import com.bank.governingstate.entity.GoverningStateEntity;
import com.bank.governingstate.exception.GoverningStateException;
import com.bank.governingstate.model.GoverningStateResponse;
import com.bank.governingstate.repository.GoverningStateRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoverningStateService {

    private static final Logger logger = LoggerFactory.getLogger(GoverningStateService.class);

    private final GoverningStateRepository governingStateRepository;

    public GoverningStateService(GoverningStateRepository governingStateRepository) {
        this.governingStateRepository = governingStateRepository;
    }

    @LogMethodTimeExecution(enabled = true)
    @Cacheable(cacheNames = "governing-state", key = "#zipCode", sync = true)
    public GoverningStateResponse fetchGoverningStateByZipcode(String zipCode) throws GoverningStateException {
        Optional<GoverningStateEntity> governingStateEntity = fetchGoverningState(zipCode);

        if (governingStateEntity.isPresent()) {
            return GoverningStateResponse.builder()
                    .zipCode(zipCode)
                    .governingState(governingStateEntity.get().getGoverningState())
                    .build();
        } else {
            logger.error("Governing State Not Found for provided ZipCode:: {}", zipCode);
            throw new GoverningStateException(ErrorConstants.GS_001, ErrorConstants.GS_001_DESCRIPTION);
        }
    }

    public Optional<GoverningStateEntity> fetchGoverningState(String zipCode) throws GoverningStateException {
        if(StringUtils.isEmpty(zipCode)) {
            logger.error("Input Zipcode cannot be empty or null.");
            throw new GoverningStateException(ErrorConstants.GS_003, ErrorConstants.GS_003_DESCRIPTION);
        }

        try {
            return governingStateRepository.findByZipCode(zipCode);
        } catch (Exception exception) {
            logger.error("Exception occurred while fetching zipCode from database for zipCode:: {}. Exception is:: {}", zipCode, exception.getCause());
            throw new GoverningStateException(ErrorConstants.GS_002, ErrorConstants.GS_002_DESCRIPTION);
        }
    }
}
