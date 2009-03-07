<g:set var="comments" value="${commentable.comments}"></g:set>
<div id="comments" class="commentable">
	<g:render template="/commentable/comment" 
			  collection="${comments}" 
			  var="comment" 
			  plugin="commentable" 
			  noEscape="${noEscape}" />
</div>
<div id="addComment" class="addComment">
	<h1 class="addCommentTitle"><g:message code="comment.add.title" default="Post a Comment"></g:message></h1>
	<div class="addCommentDescription">
		<g:message code="comment.add.description" default=""></g:message>
	</div>
	<div id="addCommentContainer" class="addCommentContainer">
		<g:formRemote name="addCommentForm" url="[controller:'commentable',action:'add']" update="comments">
			<g:textArea id="commentBody" name="comment.body" /> <br />
			<g:hiddenField name="update" value="comments" />			
			<g:hiddenField name="commentLink.commentRef" value="${commentable.id}" />
			<g:hiddenField name="commentLink.type" value="${commentable.class.name}" />			
			<g:hiddenField name="commentPageURI" value="${request.forwardURI}"></g:hiddenField>
			<g:submitButton name="${g.message(code:'comment.post.button.name', 
											 'default':'Post')}"></g:submitButton>
		</g:formRemote>
	</div>
</div>