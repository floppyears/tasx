package edu.oregonstate.mist

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "calculate sha256sum string from string"() {
        expect:
        User.sha256sum(message) == digest

        where:
        message                                       | digest
        "hello world"                                 | "b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9"
        "the quick brown fox jumps over the lazy dog" | "05c6e08f1d9fdafa03147fcb8f82f124c76d2f70e3d989dc8aadb5e7d7450bec"
        "password"                                    | "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"
    }

    void "validate password"() {
        given:
            User testUser = new User("name", "email", password)

        expect:
            testUser.validate(["passwordTemp"]) == result

        where:
            password      | result
            "password"    | false  //  <10 characters,  <1 digit
            "mypassword"  | false  // ==10 characters,  <1 digit
            "ourpassword" | false  //  >10 characters,  <1 digit
            "password1"   | false  //  <10 characters, ==1 digit
            "apassword1"  | true   // ==10 characters, ==1 digit
            "mypassword1" | true   //  >10 characters, ==1 digit
            "pass23"      | false  //  <10 characters,  >1 digit
            "password12"  | true   // ==10 characters,  >1 digit
            "password123" | true   //  >10 characters,  >1 digit
    }

    void "only store password hash"() {
        given:
            final String VALID_PASSWORD = "password123"
            User testUser = new User("name", "email", VALID_PASSWORD)

        when:
            testUser.save(flush: true)

        then:
            testUser.passwordTemp == null
            testUser.passwordEquals(VALID_PASSWORD)
    }
}
