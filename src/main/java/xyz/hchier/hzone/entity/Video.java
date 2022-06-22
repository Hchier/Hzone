package xyz.hchier.hzone.entity;

import java.io.Serializable;
import java.util.Date;

public class Video implements Serializable {
    private Integer id;

    private String publisher;

    private String path;

    private Integer favorNum;

    private Integer commentNum;

    private String introduction;

    private Boolean selfVisible;

    private Boolean hidden;

    private Date createTime;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", publisher=").append(publisher);
        sb.append(", path=").append(path);
        sb.append(", favorNum=").append(favorNum);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", introduction=").append(introduction);
        sb.append(", selfVisible=").append(selfVisible);
        sb.append(", hidden=").append(hidden);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}