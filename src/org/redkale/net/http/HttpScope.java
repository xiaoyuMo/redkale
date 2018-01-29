/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkale.net.http;

import java.util.*;
import org.redkale.convert.*;
import org.redkale.convert.json.JsonConvert;

/**
 * HTTP模板的对象域 <br>
 * 模板引擎的核心类, 业务开发人员只有通过本类对象才能调用到模板引擎功能。 <br>
 * <p>
 * HttpServlet调用: <br>
 * <pre>
 *    &#064;HttpMapping(url = "/hello.html", auth = false)
 *    public void hello(HttpRequest req, HttpResponse resp) throws IOException {
 *        resp.finish(HttpScope.template("/hello.html").attr("content", "哈哈"));
 *    }
 * </pre>
 * <p>
 * RestService调用: <br>
 * <pre>
 *    &#064;RestMapping(name = "hello.html", auth = false)
 *    public HttpScope hello() {
 *       return HttpScope.template("hello.html").attr("content", "哈哈");
 *    }
 * </pre>
 *
 * 详情见: https://redkale.org
 *
 * @author zhangjx
 */
public class HttpScope {

    protected String template;

    protected Map<String, Object> attributes;

    public static HttpScope template(String template) {
        HttpScope rs = new HttpScope();
        return rs.template(template);
    }

    public HttpScope attr(String name, Object value) {
        if (this.attributes == null) this.attributes = new LinkedHashMap<>();
        this.attributes.put(name, value);
        return this;
    }

    public <T> T find(String name) {
        return this.attributes == null ? null : (T) this.attributes.get(name);
    }

    public <T> T find(HttpScope parent, String name) {
        T rs = this.attributes == null ? null : (T) this.attributes.get(name);
        if (rs != null) return rs;
        return parent == null ? null : parent.find(name);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @ConvertDisabled(type = ConvertType.JSON)
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return JsonConvert.root().convertTo(this);
    }
}
