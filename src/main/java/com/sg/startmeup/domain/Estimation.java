package com.sg.startmeup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sg.startmeup.domain.util.CustomLocalDateSerializer;
import com.sg.startmeup.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Estimation.
 */
@Entity
@Table(name = "ESTIMATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estimation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "creadate")
    private LocalDate creadate;

    @Column(name = "pledged")
    private Boolean pledged;

    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;

    @OneToMany(mappedBy = "estimation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pledge> pledges = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreadate() {
        return creadate;
    }

    public void setCreadate(LocalDate creadate) {
        this.creadate = creadate;
    }

    public Boolean getPledged() {
        return pledged;
    }

    public void setPledged(Boolean pledged) {
        this.pledged = pledged;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Pledge> getPledges() {
        return pledges;
    }

    public void setPledges(Set<Pledge> pledges) {
        this.pledges = pledges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Estimation estimation = (Estimation) o;

        if ( ! Objects.equals(id, estimation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Estimation{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", creadate='" + creadate + "'" +
                ", pledged='" + pledged + "'" +
                ", cost='" + cost + "'" +
                '}';
    }
}
