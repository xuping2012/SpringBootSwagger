package com.xp.test.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

public class LoginParam {

	@Schema(name = "登录入参")
	@Data
	public static class getTokenByMobileInput {

		@ApiModelProperty(value = "区号", required = true)
		@NotBlank(message = "区号不能为空")
		@Pattern(regexp = "^\\d*$", message = "区号必须为数字")
		@Length(min = 2, max = 8, message = "区号区间2-8")
		private String zone;

		@ApiModelProperty(value = "手机号", required = true)
		@NotBlank(message = "手机号不能为空")
		@Pattern(regexp = "^\\d+$", message = "手机号不正确")
		@Length(min = 8, max = 20, message = "手机长度区间8-20")
		private String mobile;

		@ApiModelProperty(value = "密码", required = true)
		@NotBlank(message = "密码不能为空")
		// 包含大写字母、小写字母、特殊符号、数字中的任意三项:^(?![A-Za-z]+$)(?![A-Z0-9]+$)(?![a-z0-9]+$)(?![a-z\\W]+$)(?![A-Z\\W]+$)(?![0-9\\W]+$)[a-zA-Z0-9\\W]+$
		@Pattern(regexp = "^\\d+$", message = "密码不正确")
		@Length(min = 8, max = 16, message = "密码长度区间8-16")
		private String passwd;

	}

	@ApiModel(value = "出参")
	@Data
	public static class TokenOutput {
		@ApiModelProperty(value = "系统token(为null时, 需要执行绑定逻辑)")
		private String token;
	}

}