package com.asm.entity;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.util.Calendar;

@Entity
public class Category {
    @Id
    private long id;
    private String name;
    private String description;
    private long createdAtMLS;
    private long updatedAtMLS;
    private long deletedAtMLS;
    private int status;

    public Category() {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.createdAtMLS = Calendar.getInstance().getTimeInMillis();
        this.updatedAtMLS = Calendar.getInstance().getTimeInMillis();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public void setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public void setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
    }

    public long getDeletedAtMLS() {
        return deletedAtMLS;
    }

    public void setDeletedAtMLS(long deletedAtMLS) {
        this.deletedAtMLS = deletedAtMLS;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static final class Builder {
        private long id;
        private String name;
        private String description;
        private long createdAtMLS;
        private long updatedAtMLS;
        private long deletedAtMLS;
        private int status;

        private Builder() {
            this.id = Calendar.getInstance().getTimeInMillis();
            this.status = 1;
        }

        public static Builder aCategory() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCreatedAtMLS(long createdAtMLS) {
            this.createdAtMLS = createdAtMLS;
            return this;
        }

        public Builder withUpdatedAtMLS(long updatedAtMLS) {
            this.updatedAtMLS = updatedAtMLS;
            return this;
        }

        public Builder withDeletedAtMLS(long deletedAtMLS) {
            this.deletedAtMLS = deletedAtMLS;
            return this;
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }

        public Category build() {

            Category category = new Category();
            category.setId(id);
            category.setName(name);
            category.setDescription(description);
            category.setStatus(status);
            return category;
        }
    }
}
