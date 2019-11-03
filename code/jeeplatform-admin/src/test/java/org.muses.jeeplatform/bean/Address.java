package org.muses.jeeplatform.bean;

/**
 * <pre>
 *
 * </pre>
 *
 * @author nicky
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2019年11月03日  修改内容:
 * </pre>
 */
public class Address {
    private String tel;
    private String name;

    @Override
    public String toString() {
        return "Address{" +
                       "tel='" + tel + '\'' +
                       ", name='" + name + '\'' +
                       '}';
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
