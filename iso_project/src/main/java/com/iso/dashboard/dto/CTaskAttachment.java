package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the c_task_attachment database table.
 *
 */
@Entity
@Table(name = "c_task_attachment")
@NamedQuery(name = "CTaskAttachment.findAll", query = "SELECT c FROM CTaskAttachment c")
public class CTaskAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "hist_id")
    private Integer histSeq;

    @EmbeddedId
    private CTaskAttachmentPK id;

    public CTaskAttachmentPK getId() {
        return id;
    }

    public Integer getHistSeq() {
        return histSeq;
    }

    public void setHistSeq(Integer histSeq) {
        this.histSeq = histSeq;
    }

    public void setId(CTaskAttachmentPK id) {
        this.id = id;
    }

    public CTaskAttachment() {
    }

    public String getAttachmentUrl() {
        return this.attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
