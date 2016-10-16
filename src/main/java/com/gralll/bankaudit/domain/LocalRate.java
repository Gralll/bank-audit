package com.gralll.bankaudit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gralll.bankaudit.domain.enumeration.CheckCategory;

import com.gralll.bankaudit.domain.enumeration.Documentation;

import com.gralll.bankaudit.domain.enumeration.Execution;

/**
 * A LocalRate.
 */
@Entity
@Table(name = "local_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "localrate")
public class LocalRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "index_rate", nullable = false)
    private String indexRate;

    @NotNull
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "necessary", nullable = false)
    private Boolean necessary;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CheckCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "documentation")
    private Documentation documentation;

    @Enumerated(EnumType.STRING)
    @Column(name = "execution")
    private Execution execution;

    @Column(name = "not_rated")
    private Boolean notRated;

    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JsonIgnore
    private GroupRate groupRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndexRate() {
        return indexRate;
    }

    public LocalRate indexRate(String indexRate) {
        this.indexRate = indexRate;
        return this;
    }

    public void setIndexRate(String indexRate) {
        this.indexRate = indexRate;
    }

    public String getQuestion() {
        return question;
    }

    public LocalRate question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean isNecessary() {
        return necessary;
    }

    public LocalRate necessary(Boolean necessary) {
        this.necessary = necessary;
        return this;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public CheckCategory getCategory() {
        return category;
    }

    public LocalRate category(CheckCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(CheckCategory category) {
        this.category = category;
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public LocalRate documentation(Documentation documentation) {
        this.documentation = documentation;
        return this;
    }

    public void setDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public Execution getExecution() {
        return execution;
    }

    public LocalRate execution(Execution execution) {
        this.execution = execution;
        return this;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    public Boolean isNotRated() {
        return notRated;
    }

    public LocalRate notRated(Boolean notRated) {
        this.notRated = notRated;
        return this;
    }

    public void setNotRated(Boolean notRated) {
        this.notRated = notRated;
    }

    public Double getRate() {
        return rate;
    }

    public LocalRate rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public GroupRate getGroupRate() {
        return groupRate;
    }

    public LocalRate groupRate(GroupRate groupRate) {
        this.groupRate = groupRate;
        return this;
    }

    public void setGroupRate(GroupRate groupRate) {
        this.groupRate = groupRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocalRate localRate = (LocalRate) o;
        if(localRate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localRate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LocalRate{" +
            "id=" + id +
            ", indexRate='" + indexRate + "'" +
            ", question='" + question + "'" +
            ", necessary='" + necessary + "'" +
            ", category='" + category + "'" +
            ", documentation='" + documentation + "'" +
            ", execution='" + execution + "'" +
            ", notRated='" + notRated + "'" +
            ", rate='" + rate + "'" +
            '}';
    }
}
