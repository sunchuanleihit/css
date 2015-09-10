package com.loukou.css.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.loukou.css.enums.AuthorityTypeEnum;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AuthPassport {
	public boolean needValidate() default true;
	public AuthorityTypeEnum[] authorityTypes() default {};
}
