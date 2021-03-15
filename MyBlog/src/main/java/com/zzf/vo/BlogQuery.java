package com.zzf.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzf
 * @date 2021-02-16
 */
@Data
@NoArgsConstructor
public class BlogQuery {

    private String title;
    private Long typeId;
    private String articleType;
}
