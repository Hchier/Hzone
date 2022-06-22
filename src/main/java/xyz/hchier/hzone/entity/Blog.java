package xyz.hchier.hzone.entity;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable {
    private Integer id;

    private String publisher;

    private String title;

    private String content;

    private Integer favorNum;

    private Integer commentNum;

    private Boolean selfVisible;

    private Boolean hidden;

    private Date updateTime;

    private String tags;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getFavorNum() {
        return favorNum;
    }

    public void setFavorNum(Integer favorNum) {
        this.favorNum = favorNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Boolean getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(Boolean selfVisible) {
        this.selfVisible = selfVisible;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", publisher=").append(publisher);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", favorNum=").append(favorNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", selfVisible=").append(selfVisible);
        sb.append(", hidden=").append(hidden);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", tags=").append(tags);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}