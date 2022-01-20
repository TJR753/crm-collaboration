package com.example.crm.workbench.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivityRemark {
   private String id;//主键
   private String noteContent;//活动备注
   private String createTime;//创建时间
   private String createBy;//创建人
   private String editTime;//修改时间
   private String editBy;//修改人
   private String editFlag;//修改标志，1修改
   private String activityId;//活动id

}
