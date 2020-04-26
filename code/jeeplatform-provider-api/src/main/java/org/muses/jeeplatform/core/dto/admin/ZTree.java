package org.muses.jeeplatform.core.dto.admin;

import java.io.Serializable;

/**
 * Created by op.43027 on 2017/5/31 0031.
 */
public class ZTree implements Serializable{
    private String id;//节点id
    private String pId;//父节点pId I必须大写
    private String name;//节点名称
    private String open = "false";//是否展开树节点，默认不展开

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

}
