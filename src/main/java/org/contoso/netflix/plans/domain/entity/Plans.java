package org.contoso.netflix.plans.domain.entity;

import lombok.Getter;

@Getter
public enum Plans {
    FREE("Free"),
    PRO("Pro"),
    ELITE("Elite");

    private final String name;

    Plans(String name) {
        this.name = name;
    }
}
