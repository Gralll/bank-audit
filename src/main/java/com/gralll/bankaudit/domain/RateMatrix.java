package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RateMatrix.
 */
@Entity
@Table(name = "rate_matrix")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ratematrix")
public class RateMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "index_rate")
    private String indexRate;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "zero")
    private Integer zero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexRate() {
        return indexRate;
    }

    public RateMatrix indexRate(String indexRate) {
        this.indexRate = indexRate;
        return this;
    }

    public void setIndexRate(String indexRate) {
        this.indexRate = indexRate;
    }

    public Double getRate() {
        return rate;
    }

    public RateMatrix rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getZero() {
        return zero;
    }

    public RateMatrix zero(Integer zero) {
        this.zero = zero;
        return this;
    }

    public void setZero(Integer zero) {
        this.zero = zero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateMatrix rateMatrix = (RateMatrix) o;
        if(rateMatrix.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rateMatrix.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RateMatrix{" +
            "id=" + id +
            ", indexRate='" + indexRate + "'" +
            ", rate='" + rate + "'" +
            ", zero='" + zero + "'" +
            '}';
    }
}
