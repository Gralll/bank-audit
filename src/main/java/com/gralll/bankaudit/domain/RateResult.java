package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

import static org.hibernate.annotations.CascadeType.ALL;

/**
 * A RateResult.
 */
@Entity
@Table(name = "rate_result")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "rateresult")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = RateResult.EntityIdResolver.class, property = "id", scope=RateResult.class)
public class RateResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "rateResult", fetch=FetchType.EAGER)
    @Cascade(ALL)
    private Set<RateMatrix> rateMatrix = new HashSet<>();

    @OneToMany(mappedBy = "rateResult", fetch=FetchType.EAGER)
    @Cascade(ALL)
    private Set<GroupDiagram> groupDiagram = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private AdditionalRate additionalRate;

    @OneToOne
    @JoinColumn(unique = true)
    private FinalRate finalRate;

    @OneToOne
    @JoinColumn(unique = true)
    private EvDiagram evDiagram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<RateMatrix> getRateMatrix() {
        return rateMatrix;
    }

    public RateResult rateMatrix(Set<RateMatrix> rateMatrix) {
        this.rateMatrix = rateMatrix;
        return this;
    }

    public void setRateMatrix(Set<RateMatrix> rateMatrix) {
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

    public Set<GroupDiagram> getGroupDiagram() {
        return groupDiagram;
    }

    public RateResult groupDiagram(Set<GroupDiagram> groupDiagram) {
        this.groupDiagram = groupDiagram;
        return this;
    }

    public void setGroupDiagram(Set<GroupDiagram> groupDiagram) {
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

    public static class EntityIdResolver implements ObjectIdResolver {
        public EntityIdResolver() {
        }

        @Override
        public void bindItem(
            final ObjectIdGenerator.IdKey id,
            final Object pojo) {

        }

        @Override
        public Object resolveId(final ObjectIdGenerator.IdKey id) {

            RateResult rateResult = new RateResult();
            rateResult.setId((Long)id.key);
            return rateResult;
        }

        @Override
        public ObjectIdResolver newForDeserialization(final Object context) {
            return this;
        }

        @Override
        public boolean canUseFor(final ObjectIdResolver resolverType) {
            return false;
        }
    }
}
