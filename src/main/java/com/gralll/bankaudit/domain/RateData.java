package com.gralll.bankaudit.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.gralll.bankaudit.domain.enumeration.AuditProgress;

import static org.hibernate.annotations.CascadeType.*;

/**
 * A RateData.
 */
@Entity
@Table(name = "rate_data")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ratedata")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, resolver = RateData.EntityIdResolver.class, property = "id", scope=RateData.class)
public class RateData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "progress")
    private AuditProgress progress = AuditProgress.CREATED;

    @OneToMany(mappedBy = "rateData", fetch=FetchType.EAGER)
    @Cascade(ALL)
    //@JsonManagedReference
    //@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupRate> groupRates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditProgress getProgress() {
        return progress;
    }

    public RateData progress(AuditProgress progress) {
        this.progress = progress != null ? progress : AuditProgress.CREATED;
        return this;
    }

    public void setProgress(AuditProgress progress) {
        this.progress = progress != null ? progress : AuditProgress.CREATED;
    }

    public Set<GroupRate> getGroupRates() {
        return groupRates;
    }

    public RateData groupRates(Set<GroupRate> groupRates) {
        this.groupRates = groupRates;
        return this;
    }

    public RateData addGroupRate(GroupRate groupRate) {
        groupRates.add(groupRate);
        groupRate.setRateData(this);
        return this;
    }

    public RateData removeGroupRate(GroupRate groupRate) {
        groupRates.remove(groupRate);
        groupRate.setRateData((RateData) null);
        return this;
    }

    public void setGroupRates(Set<GroupRate> groupRates) {
        this.groupRates = groupRates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RateData rateData = (RateData) o;
        if(rateData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, rateData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RateData{" +
            "id=" + id +
            ", progress='" + progress + "'" +
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

            RateData rateData = new RateData();
            rateData.setId((Long)id.key);
            return rateData;
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
