package com.MultiThreadVsSingleThreadComparision.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "new_enitity")
@NamedStoredProcedureQuery(
        name = "GetEntitiesByIdRange",
        procedureName = "getEntitiesByIdRange",
        parameters = { @StoredProcedureParameter(mode = ParameterMode.IN, name = "startId", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "endId", type = Long.class)
        },
        resultClasses = NewEnitity.class
)
public class NewEnitity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public NewEnitity() {
    }

    @Column(name = "name")
    private String name;

    public NewEnitity(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}

