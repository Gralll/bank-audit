package com.gralll.bankaudit.domain;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Audit.
 */
@Entity
@Table(name = "audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "audit")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_level")
    private String startLevel;

    @Column(name = "end_level")
    private String endLevel;

    @OneToOne(fetch=FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(unique = true)
    private RateData rateData;

    @OneToOne(fetch=FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(unique = true)
    private RateResult result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Audit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBank() {
        return bank;
    }

    public Audit bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Audit startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Audit endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStartLevel() {
        return startLevel;
    }

    public Audit startLevel(String startLevel) {
        this.startLevel = startLevel;
        return this;
    }

    public void setStartLevel(String startLevel) {
        this.startLevel = startLevel;
    }

    public String getEndLevel() {
        return endLevel;
    }

    public Audit endLevel(String endLevel) {
        this.endLevel = endLevel;
        return this;
    }

    public void setEndLevel(String endLevel) {
        this.endLevel = endLevel;
    }

    public RateData getRateData() {
        return rateData;
    }

    public Audit rateData(RateData rateData) {
        this.rateData = rateData;
        return this;
    }

    public void setRateData(RateData rateData) {
        this.rateData = rateData;
    }

    public RateResult getResult() {
        return result;
    }

    public Audit result(RateResult rateResult) {
        this.result = rateResult;
        return this;
    }

    public void setResult(RateResult rateResult) {
        this.result = rateResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Audit audit = (Audit) o;
        if(audit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, audit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Audit{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", bank='" + bank + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", startLevel='" + startLevel + "'" +
            ", endLevel='" + endLevel + "'" +
            '}';
    }
}
