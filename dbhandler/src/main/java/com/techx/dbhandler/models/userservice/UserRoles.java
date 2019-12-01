package com.techx.dbhandler.models.userservice;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "userroles")
@Getter
@Setter
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private String userId;

    @Column(name = "roleid")
    private String roleId;
}
