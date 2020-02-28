package xyz.xiaoyu995.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location implements Serializable {
    private String sid;//唯一ID
    private String sName;//姓名
    private String sNumber;//学号
    private String cName;//班级名称
    private String longitude;//经度
    private String latitude;//纬度
    private String updateTime;//更新时间
}
