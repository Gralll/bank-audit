package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FinalRate.
 */
@Entity
@Table(name = "final_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "finalrate")
public class FinalRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "evoopd")
    private Double evoopd;

    @Column(name = "ev_1_ozpd")
    private Double ev1Ozpd;

    @Column(name = "evmb")
    private Double evmb;

    @Column(name = "r")
    private Double r;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEvoopd() {
        return evoopd;
    }

    public FinalRate evoopd(Double evoopd) {
        this.evoopd = evoopd;
        return this;
    }

    public void setEvoopd(Double evoopd) {
        this.evoopd = evoopd;
    }

    public Double getEv1Ozpd() {
        return ev1Ozpd;
    }

    public FinalRate ev1Ozpd(Double ev1Ozpd) {
        this.ev1Ozpd = ev1Ozpd;
        return this;
    }

    public void setEv1Ozpd(Double ev1Ozpd) {
        this.ev1Ozpd = ev1Ozpd;
    }

    public Double getEvmb() {
        return evmb;
    }

    public FinalRate evmb(Double evmb) {
        this.evmb = evmb;
        return this;
    }

    public void setEvmb(Double evmb) {
        this.evmb = evmb;
    }

    public Double getR() {
        return r;
    }

    public FinalRate r(Double r) {
        this.r = r;
        return this;
    }

    public void setR(Double r) {
        this.r = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FinalRate finalRate = (FinalRate) o;
        if(finalRate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, finalRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FinalRate{" +
            "id=" + id +
            ", evoopd='" + evoopd + "'" +
            ", ev1Ozpd='" + ev1Ozpd + "'" +
            ", evmb='" + evmb + "'" +
            ", r='" + r + "'" +
            '}';
    }
}
