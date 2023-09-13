package com.rosendo.company.Domain.Users.Manager;

import com.rosendo.company.Domain.Users.UserDefault.ConsultUserDefaultDTO;

public class ConsultManagerDTO extends ConsultUserDefaultDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
