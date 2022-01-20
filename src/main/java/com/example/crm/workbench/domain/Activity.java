package com.example.crm.workbench.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Activity {
    private String id;//主键
    private String owner;//所有者，user的id，外键
    private String name;//所有者姓名
    private String startDate;//开始日期
    private String endDate;//结束日期
    private String cost;//成本
    private String description;//描述
    private String createTime;//创建日期
    private String createBy;//创建人
    private String editTime;//修改时间
    private String editBy;//修改人
}
