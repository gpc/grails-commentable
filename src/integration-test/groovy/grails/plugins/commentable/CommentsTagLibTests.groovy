package grails.plugins.commentable

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

import org.grails.buffer.GrailsPrintWriter
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.web.servlet.DefaultGrailsApplicationAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.grails.gsp.GroovyPagesTemplateEngine
import org.grails.plugins.testing.GrailsMockHttpServletRequest
import org.grails.plugins.testing.GrailsMockHttpServletResponse
import javax.servlet.ServletContext

@Integration
@Rollback
class CommentsTagLibTests extends Specification {

    GroovyPagesTemplateEngine groovyPagesTemplateEngine
    private GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
    private GrailsMockHttpServletResponse response = new GrailsMockHttpServletResponse()
    ServletContext servletContext

    def setup() {
    }

    def cleanup() {
        RequestContextHolder.resetRequestAttributes()
    }

    private void assertOutputEquals(String expected, String template, Map binding) {
        def sw = new StringWriter()
        def out = new GrailsPrintWriter(sw)

        GrailsWebRequest grailsWebRequest = new GrailsWebRequest(request, response,
                  new DefaultGrailsApplicationAttributes(servletContext))
        grailsWebRequest.out = out
        RequestContextHolder.requestAttributes = grailsWebRequest

        groovyPagesTemplateEngine.createTemplate(template, 'test_' + UUID.randomUUID()).make(binding).writeTo out

        System.out.println "**** ${sw.toString()}"
        assert expected == sw.toString()
    }

    void "test Each Comment"() {
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
            entry.addComment poster, "one."
            entry.addComment poster, "two."     
        
            def template = '<comments:each bean="${bean}">${comment.body}</comments:each>'
        then: 
            assertOutputEquals "one.two.", template, [bean:entry]
        when:
            template = '<comments:each bean="${null}">${comment.body}</comments:each>'
        then:
            assertOutputEquals "", template, [bean:entry]       
    }


    void "test Each Recent"() {
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
            entry.addComment poster, "one."
            entry.addComment poster, "two."
            def template = '<comments:eachRecent domain="${domain}">${comment.body}</comments:eachRecent>'  
        then:
            assertOutputEquals "two.one.", template, [domain:TestEntry]     
    }
}
