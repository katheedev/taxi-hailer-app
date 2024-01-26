package com.accelaero.driverservice.ResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private String message;
    private int status;
}
