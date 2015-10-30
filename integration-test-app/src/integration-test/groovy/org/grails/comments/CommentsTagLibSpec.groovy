package org.grails.comments

import grails.plugins.commentable.TestEntry
import grails.plugins.commentable.TestPoster
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@TestFor(CommentsTagLib)
@Rollback
class CommentsTagLibSpec extends Specification {

    void testEachComment() {
        given:
        def poster = new TestPoster(name: "fred")
        poster.save()

        when:
        def entry = new TestEntry(title: "The Entry")
        entry.addComment(poster, "My comment")

        then:
        thrown CommentException

        when:
        entry.save()
        entry.addComment poster, "one."
        entry.addComment poster, "two."
        def template = '<comments:each bean="${bean}">${comment.body}</comments:each>'

        then:
        applyTemplate(template, [bean: entry]) == "one.two."
        applyTemplate(template, [bean: null]) == ""
    }


    void testEachRecent() {
        given:
        def poster = new TestPoster(name: "fred")
        poster.save()

        when:
        def entry = new TestEntry(title: "The Entry")
        entry.addComment(poster, "My comment")
        then:
        thrown(CommentException)

        when:
        entry.save()
        entry.addComment poster, "one."
        entry.addComment poster, "two."
        def template = '<comments:eachRecent domain="${domain}">${comment.body}</comments:eachRecent>'

        then:
        applyTemplate(template, [domain: TestEntry]) == 'two.one.'
    }
}
