package com.assignment.core.common;

import com.assignment.core.exception.InvalidUsageException;
import com.assignment.core.model.RequestData;
import com.assignment.core.model.ResponseData;

public interface CommonApi <I extends RequestData, O extends ResponseData> {

    default O execute(I requestData) {
        throw new InvalidUsageException("Execute is not supported");
    }
}
