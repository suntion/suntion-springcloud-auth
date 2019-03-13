package com.suntion.auth.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import com.suntion.core.common.base.BaseObject;

import java.util.Date;

@TableName(value = "AUTH_USER")
@Accessors(chain = true)
@Data
public class AuthUser extends BaseObject{
	private static final long serialVersionUID = 1L;
	
    @TableId(type= IdType.UUID)
    private String pkid;

    private String password;

    private String disabled;

    private String supper;

    private String account;

    private String salt;


    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "CREATE_USER")
    private String createUser;

    @TableField(value = "UPDATE_TIME")
    private Date updateTime;

    @TableField(value = "UPDATE_USER")
    private String updateUser;

    @Version
    private Integer version;

    private Integer dr = 0;

}