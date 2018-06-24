package grails.wildfly13.deployment.gix

class Questionnaire {

    Long id
    String title
    //String json

    static hasMany = [questions: Question]

    static mapping = {
        version false
    }

    static constraints = {
        title blank: false, maxSize: 250, unique: true
        //json nullable: true //, maxSize: 65535
        questions(nullable: true)
    }

    @Override
    String toString() {
        return title
    }
}