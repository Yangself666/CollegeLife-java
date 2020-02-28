package xyz.xiaoyu995.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterLevel {
    private String sid;
    private String sName;
    private String sNumber;
    private Integer preLevel;
    private Integer newLevel;
}
