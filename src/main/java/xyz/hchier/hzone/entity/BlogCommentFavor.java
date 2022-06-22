package xyz.hchier.hzone.entity;

import java.io.Serializable;
import java.util.Date;

public class BlogCommentFavor implements Serializable {
    private Integer id;

    private String praiser;

    private Integer commentId;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPraiser() {
        return praiser;
    }

    public void setPraiser(String praiser) {
        this.praiser = praiser == null ? null : praiser.trim();
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
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
        sb.append(", praiser=").append(praiser);
        sb.append(", commentId=").append(commentId);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}