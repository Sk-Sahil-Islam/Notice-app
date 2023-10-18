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
    val status: Boolean = false,
    val message: String = ""
)

internal fun isValidPassword(password: String): Boolean {
    if (password.length < 8) return false
    if (password.filter { it.isDigit() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
    if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
    if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

    return true
}

private const val ERR_LEN = "Minimum 8 character required."