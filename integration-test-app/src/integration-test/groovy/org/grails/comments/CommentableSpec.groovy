/* Copyright 2006-2007 Graeme Rocher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.comments

import grails.plugins.commentable.TestEntry
import grails.plugins.commentable.TestPoster
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Integration
@Rollback
class CommentableSpec extends Specification {
    void testProxiedClassNames() {
        given:
        def poster = new TestPoster(name: "fred")
        poster.save()

        when:
        def c = new Comment(body: "test", posterId: poster.id, posterClass: "grails.plugins.commentable.TestPoster_\$\$_javassist_7")

        then:
        c.poster != null
        c.poster.id == poster.id
    }

    void testAddComment() {
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
        entry.addComment poster, "My comment"

        then:
        entry.comments.size() == 1
        entry.totalComments == 1

        when:
        def c = entry.comments[0]

        then:
        c.body == "My comment"
        c.poster == poster
    }

    void testRemoveComment() {
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
        entry.addComment poster, "My comment"

        then:
        entry.comments.size() == 1

        when:
        def c = entry.comments[0]

        then:
        c.body == "My comment"
        c.poster == poster

        when:
        entry.removeComment(c)

        then:
        entry.comments.size() == 0
    }

    void testFindAllByPoster() {
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
        entry.addComment poster, "My comment"

        then:
        entry.comments.size() == 1

        when:
        def comments = Comment.findAllByPoster(poster)

        then:
        comments.size() == 1
        Comment.countByPoster(poster) == 1
    }

    void testGetRecentComments() {
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
        entry.addComment poster, "one"
        entry.addComment poster, "two"
        entry.addComment poster, "three"

        then:
        entry.comments.size() == 3

        when:
        def recent = TestEntry.recentComments

        then:
        recent.size() == 3
        recent[0].body == "three"
    }

    void testOnAddComment() {
        setup:
        def onAddCommentCount = 0
        def entry = new TestEntry(title: "The Entry")
        entry.metaClass.onAddComment = { comment ->
            onAddCommentCount++
        }
        def poster = new TestPoster(name: "fred")
        poster.save()
        entry.save()

        when:
        entry.addComment poster, "My comment"

        then:
        onAddCommentCount == 1
    }
}
