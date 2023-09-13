package com.rosendo.company.Domain.Users.Admin;

import com.rosendo.company.Domain.Users.UserDefault.ConsultUserDefaultDTO;

public class ConsultAdminDTO extends ConsultUserDefaultDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
