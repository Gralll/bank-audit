package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AdditionalRate.
 */
@Entity
@Table(name = "additional_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "additionalrate")
public class AdditionalRate implements Serializable {

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

    @Column(name = "evbptp")
    private Double evbptp;

    @Column(name = "evbitp")
    private Double evbitp;

    @Column(name = "ev_1_ozpd")
    private Double ev1Ozpd;

    @Column(name = "ev_2_ozpd")
    private Double ev2Ozpd;

    @Column(name = "evoopd")
    private Double evoopd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEv1() {
        return ev1;
    }

    public AdditionalRate ev1(Double ev1) {
        this.ev1 = ev1;
        return this;
    }

    public void setEv1(Double ev1) {
        this.ev1 = ev1;
    }

    public Double getEv2() {
        return ev2;
    }

    public AdditionalRate ev2(Double ev2) {
        this.ev2 = ev2;
        return this;
    }

    public void setEv2(Double ev2) {
        this.ev2 = ev2;
    }

    public Double getEv3() {
        return ev3;
    }

    public AdditionalRate ev3(Double ev3) {
        this.ev3 = ev3;
        return this;
    }

    public void setEv3(Double ev3) {
        this.ev3 = ev3;
    }

    public Double getEvbptp() {
        return evbptp;
    }

    public AdditionalRate evbptp(Double evbptp) {
        this.evbptp = evbptp;
        return this;
    }

    public void setEvbptp(Double evbptp) {
        this.evbptp = evbptp;
    }

    public Double getEvbitp() {
        return evbitp;
    }

    public AdditionalRate evbitp(Double evbitp) {
        this.evbitp = evbitp;
        return this;
    }

    public void setEvbitp(Double evbitp) {
        this.evbitp = evbitp;
    }

    public Double getEv1Ozpd() {
        return ev1Ozpd;
    }

    public AdditionalRate ev1Ozpd(Double ev1Ozpd) {
        this.ev1Ozpd = ev1Ozpd;
        return this;
    }

    public void setEv1Ozpd(Double ev1Ozpd) {
        this.ev1Ozpd = ev1Ozpd;
    }

    public Double getEv2Ozpd() {
        return ev2Ozpd;
    }

    public AdditionalRate ev2Ozpd(Double ev2Ozpd) {
        this.ev2Ozpd = ev2Ozpd;
        return this;
    }

    public void setEv2Ozpd(Double ev2Ozpd) {
        this.ev2Ozpd = ev2Ozpd;
    }

    public Double getEvoopd() {
        return evoopd;
    }

    public AdditionalRate evoopd(Double evoopd) {
        this.evoopd = evoopd;
        return this;
    }

    public void setEvoopd(Double evoopd) {
        this.evoopd = evoopd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdditionalRate additionalRate = (AdditionalRate) o;
        if(additionalRate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, additionalRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdditionalRate{" +
            "id=" + id +
            ", ev1='" + ev1 + "'" +
            ", ev2='" + ev2 + "'" +
            ", ev3='" + ev3 + "'" +
            ", evbptp='" + evbptp + "'" +
            ", evbitp='" + evbitp + "'" +
            ", ev1Ozpd='" + ev1Ozpd + "'" +
            ", ev2Ozpd='" + ev2Ozpd + "'" +
            ", evoopd='" + evoopd + "'" +
            '}';
    }
}
