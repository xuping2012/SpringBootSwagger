package com.xp.test.entity;

import lombok.Data;


@Data
public class Account {

	public static final int STATUS_INVALID = 0;
	public static final int STATUS_VALID = 1;
	public static final int STATUS_UNSUBSCRIBE = 2;

	// @TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 区号
	 */
	private String zone;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 状态(0:无效 1:有效 2: 注销)
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 是否已经删除(0:false 1:true)
	 */
	private Boolean deleted;

	// 扩展字段
	// @TableField(exist = false)
	private boolean newAccount; // 表示是否是本次操作新增的账号
}
