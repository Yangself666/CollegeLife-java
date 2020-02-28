package xyz.xiaoyu995.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    private String sid;
    private String openid;
    private String sName;
    private String sNumber;
    private Integer cid;
    private Integer level;
}
