package com.api.mobigenz_be.controllers.admin.vm;

import com.api.mobigenz_be.constants.Constant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginVM {
    //    @Scha(description = "User name of the User.", example = Constants.Api.FieldExample.USER_NAME, required = true)
    @NotNull(message = Constant.ValidationMessage.FIELD_IS_REQUIRED)
    @Email
    private String email;

    //    @Schema(description = "Password of the User.", example = Constants.Api.FieldExample.PASSWORD, required = true)
    @NotNull
    @Size(min = 2, max = 100, message = Constant.ValidationMessage.INVALID_SIZE_VALUE)
    @Pattern(regexp = "^[\\w\\d$&+,:;=?@#|'<>.^*()%!-]*", message = Constant.ValidationMessage.INVALID_PASSWORD)
    private String password;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean rememberMe = true;
}

