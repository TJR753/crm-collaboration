package com.example.crm.settings.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(description = "登录用户")
public class User {
    @ApiModelProperty(notes = "uuid,主键")
    private String id ;
    @ApiModelProperty(notes = "登陆账号")
    private String loginAct;
    @ApiModelProperty(notes = "用户真实姓名")
    private String name;
    @ApiModelProperty(notes = "密码不能采用明文存储，采用密文，MD5加密之后的数据")
    private String loginPwd;
    @ApiModelProperty(notes = "注册邮箱")
    private String email;
    @ApiModelProperty(notes = "19位数;失效时间为空的时候表示永不失效，失效时间为2018-10-10 10:10:10，则表示在该时间之前该账户可用。")
    private String expireTime;
    @ApiModelProperty(notes = "锁定状态为空时表示启用，为0时表示锁定，为1时表示启用。")
    private String lockState;
    @ApiModelProperty(notes = "部门编号")
    private String deptno;
    @ApiModelProperty(notes = "允许访问的IP为空时表示IP地址永不受限，允许访问的IP可以是一个，也可以是多个，当多个IP地址的时候，采用半角逗号分隔。允许IP是192.168.100.2，表示该用户只能在IP地址为192.168.100.2的机器上使用。")
    private String allowIps;
    @ApiModelProperty(notes = "创建时间")
    private String createTime;
    @ApiModelProperty(notes = "创建人")
    private String createBy;
    @ApiModelProperty(notes = "修改时间")
    private String editTime;
    @ApiModelProperty(notes = "修改人")
    private String editBy;

}
