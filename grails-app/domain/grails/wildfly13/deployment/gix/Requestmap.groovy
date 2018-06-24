package grails.wildfly13.deployment.gix

import org.springframework.http.HttpMethod

class Requestmap implements Serializable {

    private static final long serialVersionUID = 1

    String configAttribute
    HttpMethod httpMethod
    String featureName
    String url
    String type

    Requestmap(String featureName, String url, String configAttribute, String type, HttpMethod httpMethod = null) {
        this()
        this.configAttribute = configAttribute
        this.httpMethod = httpMethod
        this.url = url
        this.type = type
        this.featureName = featureName
    }

    static constraints = {
        featureName blank: false
        url blank: false, unique: 'httpMethod'
        configAttribute blank: false
        type nullable: true, inList: ["MENU", "LINK", "BUTTON", "FORM_ACTION"]
        httpMethod nullable: true
    }

    static mapping = {
        cache true
    }
}