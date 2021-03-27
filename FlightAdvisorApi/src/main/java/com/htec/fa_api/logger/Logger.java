package com.htec.fa_api.logger;

import com.htec.fa_api.model.User;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Logger {
    private Integer id;
    private String actionType;
    private String actionDetails;
    private String tableName;
    private Timestamp created;
    private User user; //creator
    private Byte atomic;

    public Logger() {
    }

    public Logger(String actionType, String actionDetails, String tableName, User user) {
        this.actionType = actionType;
        this.actionDetails = actionDetails;
        this.tableName = tableName;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "actionType", nullable = false)
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Basic
    @Column(name = "actionDetails", nullable = true)
    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }
    @Basic
    @Column(name = "tableName", nullable = false)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "atomic", nullable = false, insertable = false) //todo check
    @ColumnDefault(value = "1")
    public Byte getAtomic() {
        return atomic;
    }

    public void setAtomic(Byte atomic) {
        this.atomic = atomic;
    }
}
