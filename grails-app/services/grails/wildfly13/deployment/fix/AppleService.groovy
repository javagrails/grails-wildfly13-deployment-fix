package grails.wildfly13.deployment.fix

import grails.gorm.services.Service

@Service(Apple)
interface AppleService {

    Apple get(Serializable id)

    List<Apple> list(Map args)

    Long count()

    void delete(Serializable id)

    Apple save(Apple apple)

}