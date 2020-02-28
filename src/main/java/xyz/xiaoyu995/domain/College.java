package xyz.xiaoyu995.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class College implements Serializable {
    private Integer coid;
    private String coName;
    private String notice;
    private String grade;
}
