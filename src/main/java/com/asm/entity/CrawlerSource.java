package com.asm.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Calendar;
@Entity
public class CrawlerSource {
    @Id
    private long id;
    //thong tin chung
    @Index
    private String url;
    @Index
    private String linkSelector;
    //lay chi tiet
    private String titleSelector;
    private String descriptionSelector;
    private String contentSelector;
    private String authorSelector;
    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;
    private long deleteaAtMLS;
    @Index
    private int status; //1 active 2 deactive

    public CrawlerSource() {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.createdAtMLS = Calendar.getInstance().getTimeInMillis();
        this.updatedAtMLS = Calendar.getInstance().getTimeInMillis();
        this.status = 1;
    }

    public String getAuthorSelector() {
        return authorSelector;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getLinkSelector() {
        return linkSelector;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public String getDescriptionSelector() {
        return descriptionSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public long getDeleteaAtMLS() {
        return deleteaAtMLS;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLinkSelector(String linkSelector) {
        this.linkSelector = linkSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public void setDescriptionSelector(String descriptionSelector) {
        this.descriptionSelector = descriptionSelector;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public void setAuthorSelector(String authorSelector) {
        this.authorSelector = authorSelector;
    }

    public void setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
    }

    public void setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
    }

    public void setDeleteaAtMLS(long deleteaAtMLS) {
        this.deleteaAtMLS = deleteaAtMLS;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static final class Builder {
        private long id;
        //thong tin chung
        private String url;
        private long categoryId;
        private String linkSelector;
        //lay chi tiet
        private String titleSelector;
        private String descriptionSelector;
        private String contentSelector;
        private long createdAtMLS;
        private long updatedAtMLS;
        private long deleteaAtMLS;
        private int status; //1 active 2 deactive

        private Builder() {

        }

        public static Builder aCrawlerSource() {
            return new Builder();
        }

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withCategoryId(long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withLinkSelector(String linkSelector) {
            this.linkSelector = linkSelector;
            return this;
        }

        public Builder withTitleSelector(String titleSelector) {
            this.titleSelector = titleSelector;
            return this;
        }

        public Builder withDescriptionSelector(String descriptionSelector) {
            this.descriptionSelector = descriptionSelector;
            return this;
        }

        public Builder withContentSelector(String contentSelector) {
            this.contentSelector = contentSelector;
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

        public Builder withDeleteaAtMLS(long deleteaAtMLS) {
            this.deleteaAtMLS = deleteaAtMLS;
            return this;
        }

        public Builder withStatus(int status) {
            this.status = status;
            return this;
        }

        public CrawlerSource build() {
            CrawlerSource crawlerSource = new CrawlerSource();
            crawlerSource.descriptionSelector = this.descriptionSelector;
            crawlerSource.status = this.status;
            crawlerSource.url = this.url;
            crawlerSource.linkSelector = this.linkSelector;
            crawlerSource.titleSelector = this.titleSelector;
            crawlerSource.contentSelector = this.contentSelector;
            return crawlerSource;
        }
    }
}
