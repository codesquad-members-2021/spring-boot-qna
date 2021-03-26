package com.codessquad.qna.exception;

import com.codessquad.qna.utils.AccountError;

public class UserAccountException extends RuntimeException {

    private AccountError accountError;

    public UserAccountException(AccountError accountError) {
        super(accountError.getErrorMessage());
        this.accountError = accountError;
    }

    public AccountError getUserAccountError() {
        return accountError;
    }
}
