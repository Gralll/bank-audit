package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EvDiagram.
 */
@Entity
@Table(name = "ev_diagram")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "evdiagram")
public class EvDiagram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ev_1")
    private Double ev1;

    @Column(name = "ev_2")
    private Double ev2;

    @Column(name = "ev_3")
    private Double ev3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEv1() {
        return ev1;
    }

    public EvDiagram ev1(Double ev1) {
        this.ev1 = ev1;
        return this;
    }

    public void setEv1(Double ev1) {
        this.ev1 = ev1;
    }

    public Double getEv2() {
        return ev2;
    }

    public EvDiagram ev2(Double ev2) {
        this.ev2 = ev2;
        return this;
    }

    public void setEv2(Double ev2) {
        this.ev2 = ev2;
    }

    public Double getEv3() {
        return ev3;
    }

    public EvDiagram ev3(Double ev3) {
        this.ev3 = ev3;
        return this;
    }

    public void setEv3(Double ev3) {
        this.ev3 = ev3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EvDiagram evDiagram = (EvDiagram) o;
        if(evDiagram.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, evDiagram.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EvDiagram{" +
            "id=" + id +
            ", ev1='" + ev1 + "'" +
            ", ev2='" + ev2 + "'" +
            ", ev3='" + ev3 + "'" +
            '}';
    }
}
