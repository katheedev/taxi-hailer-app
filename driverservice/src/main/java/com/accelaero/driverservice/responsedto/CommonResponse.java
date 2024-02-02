package com.accelaero.driverservice.responsedto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String message;
    private int status;
}
