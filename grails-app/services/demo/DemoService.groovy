package demo

import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional

@Transactional
class DemoService {

    def wrapped() { 'expected: transactional' }

    @NotTransactional
    def optedOut() { 'expected: NOT transactional' }
}
