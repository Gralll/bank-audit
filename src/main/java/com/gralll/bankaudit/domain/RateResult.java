package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RateResult.
 */
@Entity
@Table(name = "rate_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rateresult")
public class RateResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private RateMatrix rateMatrix;

    @OneToOne
    @JoinColumn(unique = true)
    private AdditionalRate additionalRate;

    @OneToOne
    @JoinColumn(unique = true)
    private FinalRate finalRate;

    @OneToOne
    @JoinColumn(unique = true)
    private EvDiagram evDiagram;

    @OneToOne
    @JoinColumn(unique = true)
    private GroupDiagram groupDiagram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RateMatrix getRateMatrix() {
        return rateMatrix;
    }

    public RateResult rateMatrix(RateMatrix rateMatrix) {
        this.rateMatrix = rateMatrix;
        return this;
    }

    public void setRateMatrix(RateMatrix rateMatrix) {
        this.rateMatrix = rateMatrix;
    }

    public AdditionalRate getAdditionalRate() {
        return additionalRate;
    }

    public RateResult additionalRate(AdditionalRate additionalRate) {
        this.additionalRate = additionalRate;
        return this;
    }

    public void setAdditionalRate(AdditionalRate additionalRate) {
        this.additionalRate = additionalRate;
    }

    public FinalRate getFinalRate() {
        return finalRate;
    }

    public RateResult finalRate(FinalRate finalRate) {
        this.finalRate = finalRate;
        return this;
    }

    public void setFinalRate(FinalRate finalRate) {
        this.finalRate = finalRate;
    }

    public EvDiagram getEvDiagram() {
        return evDiagram;
    }

    public RateResult evDiagram(EvDiagram evDiagram) {
        this.evDiagram = evDiagram;
        return this;
    }

    public void setEvDiagram(EvDiagram evDiagram) {
        this.evDiagram = evDiagram;
    }

    public GroupDiagram getGroupDiagram() {
        return groupDiagram;
    }

    public RateResult groupDiagram(GroupDiagram groupDiagram) {
        this.groupDiagram = groupDiagram;
        return this;
    }

    public void setGroupDiagram(GroupDiagram groupDiagram) {
        this.groupDiagram = groupDiagram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateResult rateResult = (RateResult) o;
        if(rateResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rateResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RateResult{" +
            "id=" + id +
            '}';
    }
}
