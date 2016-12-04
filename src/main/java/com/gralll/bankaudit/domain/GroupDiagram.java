package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

/**
 * A GroupDiagram.
 */
@Entity
@Table(name = "group_diagram")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "groupdiagram")
public class GroupDiagram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "index_rate")
    private String indexRate;

    @Column(name = "value")
    private Double value;

    @Column(name = "level")
    private Integer level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="RATE_RESULT_ID")
    @JsonIdentityReference(alwaysAsId = true)
    private RateResult rateResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexRate() {
        return indexRate;
    }

    public GroupDiagram indexRate(String indexRate) {
        this.indexRate = indexRate;
        return this;
    }

    public void setIndexRate(String indexRate) {
        this.indexRate = indexRate;
    }

    public Double getValue() {
        return value;
    }

    public GroupDiagram value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getLevel() {
        return level;
    }

    public GroupDiagram level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public RateResult getRateResult() {
        return rateResult;
    }

    public GroupDiagram rateResult(RateResult rateResult) {
        this.rateResult = rateResult;
        return this;
    }

    public void setRateResult(RateResult rateResult) {
        this.rateResult = rateResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupDiagram groupDiagram = (GroupDiagram) o;
        if(groupDiagram.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, groupDiagram.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GroupDiagram{" +
            "id=" + id +
            ", indexRate='" + indexRate + "'" +
            ", value='" + value + "'" +
            ", level='" + level + "'" +
            '}';
    }
}
