import org.grails.comments.*

class TestController {

	static initialized = false
	def index = {
		def poster 
		def entry
		if(!initialized) {
			poster = new TestPoster(name:"fred")
			poster.save()

			entry = new TestEntry(title:"test")
			entry.save()	
			
			entry.addComment poster, "one."
			entry.addComment poster, "two."		
			
			initialized = true		
		}
		else {
			poster = TestPoster.get(1)
			entry = TestEntry.get(1)
		}
			
			
		[entry:entry]		
	}

}