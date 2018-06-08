package grails.wildfly13.deployment.fix

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class OpenControllerSpec extends Specification implements ControllerUnitTest<OpenController> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
