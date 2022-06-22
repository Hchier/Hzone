package xyz.hchier.hzone.entity;

import java.io.Serializable;
import java.util.Date;

public class VideoComment implements Serializable {
    private Integer id;

    private String publisher;

    private Integer videoId;

    private String content;

    private Integer commentNum;

    private Integer favorNum;

    private Boolean hidden;

    private Boolean deleted;

    private Integer sourceComment;

    private Integer commentOf;

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

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getFavorNum() {
        return favorNum;
    }

    public void setFavorNum(Integer favorNum) {
        this.favorNum = favorNum;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getSourceComment() {
        return sourceComment;
    }

    public void setSourceComment(Integer sourceComment) {
        this.sourceComment = sourceComment;
    }

    public Integer getCommentOf() {
        return commentOf;
    }

    public void setCommentOf(Integer commentOf) {
        this.commentOf = commentOf;
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
        sb.append(", videoId=").append(videoId);
        sb.append(", content=").append(content);
        sb.append(", commentNum=").append(commentNum);
        sb.append(", favorNum=").append(favorNum);
        sb.append(", hidden=").append(hidden);
        sb.append(", deleted=").append(deleted);
        sb.append(", sourceComment=").append(sourceComment);
        sb.append(", commentOf=").append(commentOf);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}