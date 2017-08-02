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

import grails.test.*
/* TODO
class CommentableTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

	void testProxiedClassNames() {
		def poster = new TestPoster(name:"fred")
		poster.save()

		def c = new Comment(body:"test", posterId:poster.id, posterClass:"org.grails.comments.TestPoster_\$\$_javassist_7")
		
		assertNotNull c.poster
		assertEquals poster.id, c.poster.id
	}
    void testAddComment() {
		def poster = new TestPoster(name:"fred")
		poster.save()
		
		def entry = new TestEntry(title:"The Entry")
		shouldFail(CommentException) {
			entry.addComment(poster, "My comment")			
		}

		entry.save()
		
		entry.addComment poster, "My comment"
		
		assertEquals 1, entry.comments.size()
		assertEquals 1, entry.totalComments
		
		def c = entry.comments[0]
		
		assertEquals "My comment", c.body
		assertEquals poster, c.poster
    }

	void testRemoveComment() {
		def poster = new TestPoster(name:"fred")
		poster.save()
		
		def entry = new TestEntry(title:"The Entry")
		shouldFail(CommentException) {
			entry.addComment(poster, "My comment")			
		}

		entry.save()
		
		entry.addComment poster, "My comment"
		
		assertEquals 1, entry.comments.size()
		
		def c = entry.comments[0]
		
		assertEquals "My comment", c.body
		assertEquals poster, c.poster		
		
		entry.removeComment(c)
		
		assertEquals 0, entry.comments.size()		
	}
	
	void testFindAllByPoster() {
		def poster = new TestPoster(name:"fred")
		poster.save()

		def entry = new TestEntry(title:"The Entry")
		shouldFail(CommentException) {
			entry.addComment(poster, "My comment")			
		}

		entry.save()

		entry.addComment poster, "My comment"

		assertEquals 1, entry.comments.size()
		
		def comments = Comment.findAllByPoster(poster)		
		
		assertEquals 1, comments.size()
		
		assertEquals 1, Comment.countByPoster(poster)
	}
	
	void testGetRecentComments() {
		def poster = new TestPoster(name:"fred")
		poster.save()

		def entry = new TestEntry(title:"The Entry")
		shouldFail(CommentException) {
			entry.addComment(poster, "My comment")			
		}

		entry.save()

		entry.addComment poster, "one"
		entry.addComment poster, "two"
		entry.addComment poster, "three"				

		assertEquals 3, entry.comments.size()
		
		def recent = TestEntry.recentComments
		assertEquals 3, recent.size()
		assertEquals "three", recent[0].body
	}

    void testOnAddComment() {
        def poster = new TestPoster(name:"fred")
		poster.save()

		def entry = new TestEntry(title:"The Entry")
		
		def onAddCommentCalled = false
		entry.metaClass.onAddComment = { comment -> 
		    onAddCommentCalled = true
		    assertEquals poster, comment.poster
		    assertEquals "My comment", comment.body
		}
        
		entry.save()

		entry.addComment poster, "My comment"

        assertTrue "onAddComment() was never called", onAddCommentCalled
    }
}
*/