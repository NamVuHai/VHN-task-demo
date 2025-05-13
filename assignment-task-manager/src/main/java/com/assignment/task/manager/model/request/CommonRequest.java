package com.assignment.task.manager.model.request;

import com.assignment.core.model.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonRequest extends RequestData {
    private String data;
}
