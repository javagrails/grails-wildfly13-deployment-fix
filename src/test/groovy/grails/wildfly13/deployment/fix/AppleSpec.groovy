package grails.wildfly13.deployment.fix

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AppleSpec extends Specification implements DomainUnitTest<Apple> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
