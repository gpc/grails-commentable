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
package grails.plugins.commentable

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class CommentableTests extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test Proxied Class Names"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()

            def c = new Comment(body:"test", posterId:poster.id, posterClass:"grails.plugins.commentable.TestPoster_\$\$_javassist_7")
        expect:
            c.poster
            poster.id == c.poster.id
    }

    void "test Add Comment"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()
            def entry = new TestEntry(title:"The Entry")
        when:
            entry.addComment(poster, "My comment") 
        then:
            thrown CommentException
        when:
            entry.save()
            entry.addComment poster, "My comment"
        then:
            1 == entry.comments.size()
            1 == entry.totalComments
        when:
            def c = entry.comments[0]
        then:
            "My comment" == c.body && poster == c.poster
    }

    void "test Remove Comment"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()
            def entry = new TestEntry(title:"The Entry")
        when:
            entry.addComment(poster, "My comment") 
        then:
            thrown CommentException
        when:
            entry.save()
            entry.addComment poster, "My comment"
        then:
            1 == entry.comments.size()
            1 == entry.totalComments
        when:
            def c = entry.comments[0]
        then:
            "My comment" == c.body
            poster == c.poster     
        when:
            entry.removeComment(c)
        then:
            0 == entry.comments.size()       
    }
    
    void "test Find All By Poster"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()
            def entry = new TestEntry(title:"The Entry")
        when:
            entry.addComment(poster, "My comment") 
        then:
            thrown CommentException
        when:
            entry.save()
            entry.addComment poster, "My comment"
        then:
            1 == entry.comments.size()
            1 == entry.totalComments
        when:
            def comments = Comment.findAllByPoster(poster)      
        then:
            1 == comments.size()
            1 == Comment.countByPoster(poster)
    }

    void "test Get Recent Comments"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()
            def entry = new TestEntry(title:"The Entry")
        when:
            entry.addComment(poster, "My comment") 
        then:
            thrown CommentException
        when:
            entry.save()

            entry.addComment poster, "one"
            entry.addComment poster, "two"
            entry.addComment poster, "three"                
        then:
            3 == entry.comments.size()
        when:
            def recent = TestEntry.recentComments
        then:
            3 == recent.size()
            "three" == recent[0].body
    }

    void "test On Add Comment"() {
        given:
            def poster = new TestPoster(name:"fred")
            poster.save()
            def entry = new TestEntry(title:"The Entry")
        when:
            def onAddCommentCalled = false
            entry.metaClass.onAddComment = { comment -> 
                onAddCommentCalled = true
                poster == comment.poster
                "My comment" == comment.body
            }
        
            entry.save()

            entry.addComment poster, "My comment"
        then: "onAddComment() was never called"
            onAddCommentCalled 
    }
}