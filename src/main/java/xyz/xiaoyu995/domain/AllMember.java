package xyz.xiaoyu995.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllMember implements Serializable {
    private String sid;
    private String openid;
    private String sName;
    private String sNumber;
    private String cName;
    private String cNotice;
    private String coName;
    private String coNotice;
    private Integer level;
}
