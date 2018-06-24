package grails.wildfly13.deployment.gix

class Question {

    Long id
    String title
    String options = ""              // json type string or coma separated string
    String type                 // Narrative/Selective
    String onCondition = ""     // normally previousId, nextId is zero but condition basis nextId will be attach
    Long previousId = 0l
    Long nextId = 0l

    static hasOne = [answer: Answer] //Answer answer //  some how hasOne not working properly //static hasOne = [answer: Answer]
    static belongsTo = [questionnaire: Questionnaire] // table contains [ questionnaire_id ] ref key

    static mapping = {
        version false
        options sqlType: "text"
    }

    static constraints = {
        title blank: false, maxSize: 250, unique: true
        type blank: false, maxSize: 20, inList: ["NARRATIVE", "SELECTIVE"]
        options nullable: true, blank: true
        answer nullable: true, unique: true
        questionnaire(nullable: true)
    }

    @Override
    String toString() {
        return title
    }
}