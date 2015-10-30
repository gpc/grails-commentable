# Commentable Grails Plugin

This is the source for the [Commentable Grails plugin][1] which adds support for attaching comments to domain classes.

[1]: http://grails.org/plugin/commentable

## History
30/10/2015
 * Migration to Grails 3.x.
 * Removed non-nullable `title` property (causes failure in `addComment`).

07/06/2013
 * Added a `title` property to `Comment` domain object and added title to searchable index
 * Added `title` to `_comments.gsp` and a way to remove duplicate web context by adding `grails.commentable.remove.context` to config file. To use this property set it to the length of your context
 * Added `title` to `_comment.gsp`
		 
