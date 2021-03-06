package com.team33.backend.comment.controller;

import com.team33.backend.comment.controller.dto.CommentDeleteResponse;
import com.team33.backend.comment.controller.dto.CommentEditRequest;
import com.team33.backend.comment.controller.dto.CommentEditResponse;
import com.team33.backend.comment.controller.dto.CommentWriteRequest;
import com.team33.backend.comment.controller.dto.CommentWriteResponse;
import com.team33.backend.comment.service.CommentService;
import com.team33.backend.emoji.controller.dto.cache.CommentCache;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issuegroup")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{issueGroupId}/issues/{issueId}/comments")
    public CommentWriteResponse writeComment(HttpServletRequest servletRequest, @PathVariable Long issueGroupId,
                                             @PathVariable Long issueId, @Valid @RequestBody CommentWriteRequest request) {
        String githubId = (String) servletRequest.getAttribute("memberId");
        return new CommentWriteResponse(commentService.writeComment(issueGroupId, issueId, githubId, request));
    }

    @PutMapping("/{issueGroupId}/issues/{issueId}/comments/{commentId}")
    public CommentEditResponse edieComment(@PathVariable Long issueGroupId, @PathVariable Long issueId,
                                           @PathVariable Long commentId, @Valid @RequestBody CommentEditRequest request) {
        return new CommentEditResponse(commentService.editComment(commentId, request));
    }

    @DeleteMapping("/{issueGroupId}/issues/{issueId}/comments/{commentId}")
    public CommentDeleteResponse deleteComment(@PathVariable Long issueGroupId, @PathVariable Long issueId,
                                               @PathVariable Long commentId) {
        return new CommentDeleteResponse(commentService.deleteComment(commentId));
    }

    @GetMapping("/{issueGroupId}/issues/{issueId}/comments")
    public List<CommentCache> findComments(@PathVariable Long issueGroupId, @PathVariable Long issueId) {
        return commentService.findCommentsByIssueId(issueId);
    }
}
