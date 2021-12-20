package com.konb.wj.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author konb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WjException extends RuntimeException {

    private Integer code;

    private String message;

}
