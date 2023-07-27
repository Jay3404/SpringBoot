package com.bit.todoboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "T_MEMBER",
        //username UK로 지정
        uniqueConstraints =  {@UniqueConstraint(columnNames = "username")}
)
@SequenceGenerator(
        name = "MemberSeqGenerator",
        sequenceName = "T_MEMBER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
public class Member {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MemberSeqGenerator"
    )
    private long id;
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ColumnDefault("'ROLE_USER")
    private String role;

}
