package br.alura.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    USER_E,
    USER_V;

    public static final List<String> roleList =
            Arrays.stream(Role.values())
                    .map(Role::name)
                    .toList();

    public static List<Role> checkRoles(final List<String> roles) {
        return roles.stream()
                .map(Role::valueOf)
                .toList();
    }

    public static List<String> roleToString(final List<Role> roles) {
        return roles.stream()
                .map(Role::name)
                .toList();
    }
}
