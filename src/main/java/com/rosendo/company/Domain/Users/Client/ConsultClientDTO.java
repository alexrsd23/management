package com.rosendo.company.Domain.Users.Client;

import com.rosendo.company.Domain.Users.UserDefault.ConsultUserDefaultDTO;

public class ConsultClientDTO extends ConsultUserDefaultDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
