package com.fazeyna.utils;

import com.fazeyna.exceptions.DuplicatedFieldException;
import com.fazeyna.exceptions.RequiredFieldException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomValidator {
    public static void requireNonBlank(String str, String field, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new RequiredFieldException(field + " " + message);
        }
    }

    public static void requireNonBlank(String str, String field) {
        if (str == null || str.trim().isEmpty()) {
            throw new RequiredFieldException(field + " est obligatoire.");
        }
    }

    public static void requireNonBlank(Long id, String field) {
        if (id == null) {
            throw new RequiredFieldException(field + " est obligatoire.");
        }
    }

    public static void requireNonBlank(Object object) {
        if (object == null) {
            throw new RequiredFieldException("L'objet à créer est obligatoire.");
        }
    }

    public static void equalValues(String value1, String value2) {
        if (!value1.equals(value2)) {
            throw new RequiredFieldException("Les mots de passe ne sont pas identiques.");
        }
    }

    public static void equalValues(String value1, String value2, String message) {
        if (!value1.equals(value2)) {
            throw new RequiredFieldException(message);
        }
    }

    public static <T> void requireNonEmptyArray(List<T> list, String field) {
        if (list == null || list.isEmpty()) {
            throw new RequiredFieldException("Choisissez au moins " + field);
        }
    }

    public static void alreadyExists(String field) {
        throw new DuplicatedFieldException(field + " existe déjà.");
    }

    public static void validatePassword(String password) {
        int min = 8;
        int digit = 0;
        int special = 0;
        int upCount = 0;
        int loCount = 0;

        if (password.length() < min) {
            throw new IllegalArgumentException("La longueur du mot de passe doit être d'au moins 8 caractères.");
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                upCount++;
            } else if (Character.isLowerCase(c)) {
                loCount++;
            } else if (Character.isDigit(c)) {
                digit++;
            } else if (Character.toString(c).matches("[^a-zA-Z0-9]")) {
                special++;
            }
        }

        if (upCount < 1 || loCount < 1 || digit < 1 || special < 1) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");
        }
    }

}
