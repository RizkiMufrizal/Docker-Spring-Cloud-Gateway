package org.rizki.mufrizal.gateway.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse implements Serializable {
    private String code;
    private String message;
}
