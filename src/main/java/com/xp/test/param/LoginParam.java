package com.xp.test.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

public class LoginParam {

	@ApiModel(value = "入参")
	@Data
	public static class getTokenByMobileInput {

		@ApiModelProperty(value = "区号", required = true)
		@NotBlank(message = "区号不能为空")
		@Pattern(regexp = "^\\d*$", message = "区号必须为数字")
		@Length(min = 1, max = 10, message = "区号最长长度不能够大于10")
		private String zone;

		@ApiModelProperty(value = "手机号", required = true)
		@NotBlank(message = "手机号不能为空")
		@Pattern(regexp = "^\\d+$", message = "手机格式不正确")
		@Length(min = 1, max = 20, message = "手机最长长度不能够大于20")
		private String mobile;

	}

	@ApiModel(value = "出参")
	@Data
	public static class TokenOutput {
		@ApiModelProperty(value = "系统token(为null时, 需要执行绑定逻辑)")
		private String token;
	}
}