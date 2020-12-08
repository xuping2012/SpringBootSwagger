package com.xp.test.common.utils;


import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import com.xp.test.common.exception.ValidateException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;



/**
 * 异常工具
 * 
 * 
 * @author qguan
 *
 */

public final class ValidatorUtils {
    private static Validator validator;

    static {
        /**
         * 设置快速返回失败模式
         */
        validator = Validation.byDefaultProvider()
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .messageInterpolator(new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(getMessageSource())))
                .buildValidatorFactory().getValidator();
    }

    private static ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setBasenames("i18n/validation");
        return bundleMessageSource;
    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new ValidateException(constraint.getMessage());
        }
    }
}