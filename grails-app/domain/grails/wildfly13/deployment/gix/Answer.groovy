package grails.wildfly13.deployment.gix

class Answer {

    Long id
    String answer
    String questionType            // Narrative/Selective

    static belongsTo = [question: Question]  // table contains [ question_id ] ref key

    static mapping = {
        version false
        answer sqlType: "text"
    }

    static constraints = {
        answer blank: false
        questionType blank: false, maxSize: 20, inList: ["NARRATIVE", "SELECTIVE"]
        question(nullable: true, unique: true)
    }

    @Override
    String toString() {
        return answer
    }
}