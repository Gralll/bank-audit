package com.gralll.bankaudit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import static org.hibernate.annotations.CascadeType.*;

/**
 * A GroupRate.
 */
@Entity
@Table(name = "group_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "grouprate")
public class GroupRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "index_rate", nullable = false)
    private String indexRate;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JsonIgnore
    private RateData rateData;

    @OneToMany(mappedBy = "groupRate", fetch=FetchType.EAGER)
    @Cascade(ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LocalRate> localRates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GroupRate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndexRate() {
        return indexRate;
    }

    public GroupRate indexRate(String indexRate) {
        this.indexRate = indexRate;
        return this;
    }

    public void setIndexRate(String indexRate) {
        this.indexRate = indexRate;
    }

    public String getCategory() {
        return category;
    }

    public GroupRate category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getRate() {
        return rate;
    }

    public GroupRate rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public RateData getRateData() {
        return rateData;
    }

    public GroupRate rateData(RateData rateData) {
        this.rateData = rateData;
        return this;
    }

    public void setRateData(RateData rateData) {
        this.rateData = rateData;
    }

    public Set<LocalRate> getLocalRates() {
        return localRates;
    }

    public GroupRate localRates(Set<LocalRate> localRates) {
        this.localRates = localRates;
        return this;
    }

    public GroupRate addLocalRate(LocalRate localRate) {
        localRates.add(localRate);
        localRate.setGroupRate(this);
        return this;
    }

    public GroupRate removeLocalRate(LocalRate localRate) {
        localRates.remove(localRate);
        localRate.setGroupRate(null);
        return this;
    }

    public void setLocalRates(Set<LocalRate> localRates) {
        this.localRates = localRates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupRate groupRate = (GroupRate) o;
        if(groupRate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupRate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", indexRate='" + indexRate + "'" +
            ", rate='" + rate + "'" +
            '}';
    }
}
