package com.pepsiwyl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @author by pepsi-wyl
 * @date 2022-07-22 18:15
 */

@Alias("user")

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)

public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
