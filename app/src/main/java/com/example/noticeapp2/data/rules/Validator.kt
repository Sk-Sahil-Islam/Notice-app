package com.example.noticeapp2.data.rules

object Validator {

    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(email.matches(EMAIL_REGEX.toRegex()))
    }
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(!password.isNullOrEmpty() && password.length >= 6)
    }
}

data class ValidationResult(
    val status: Boolean = false
)