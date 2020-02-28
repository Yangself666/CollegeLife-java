package xyz.xiaoyu995.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempLocation {
    private String sid;
    private String longitude;//经度
    private String latitude;//纬度
    private String updateTime;//更新时间
}
