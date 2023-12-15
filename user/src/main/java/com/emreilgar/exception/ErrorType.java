package com.emreilgar.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ErrorType {
        LOGIN_ERROR_WRONG(1000, "Kullanıcı adı yada şifre hatalı", INTERNAL_SERVER_ERROR),
        INVALID_ACTIVATE_CODE(1006, "Activate Code Bulunamdı", INTERNAL_SERVER_ERROR),
        USERNAME_DUPLICATE(1007,"Kullanıcı adı bir başkası tarafından kullanılıyor.",HttpStatus.BAD_REQUEST),
        BAD_REQUEST(4100,"Parametre Hatası",HttpStatus.BAD_REQUEST),
        USER_MANAGER_EXCEPTION(4110,"Kullanıcı adı kayıtlı !" ,HttpStatus.BAD_REQUEST),
        USER_NOT_FOUND(4111,"Kullanıcı bulunamadı" ,HttpStatus.BAD_REQUEST),
        USER_DETAILS_NOT_FOUND(4112,"Kullanıcının Detayı Bulunamadı !" ,HttpStatus.BAD_REQUEST),
        USER_NOT_ACTIVE(4113,"Kullanıcı Aktif değil" ,HttpStatus.BAD_REQUEST),
        NO_ACCOUNT_FOUND_FOR_EMAIL(4114,"Mail adresiyle ilişkili hesap yok !", HttpStatus.BAD_REQUEST),
        NO_ACCOUNT_FOUND_FOR_ID(4115,"Id ile ilişkili hesap yok !", HttpStatus.BAD_REQUEST),
        INTERNAL_ERROR(5100,"Sunucuda beklenmeyen hata oluştu",HttpStatus.INTERNAL_SERVER_ERROR);

        int code;
    String message;
    HttpStatus httpStatus;



}
