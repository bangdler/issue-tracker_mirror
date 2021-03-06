package com.team33.backend.issue.repository;

import com.team33.backend.issue.domain.Issue;
import com.team33.backend.issue.domain.IssueStatus;
import com.team33.backend.issuegroup.domain.IssueGroup;
import com.team33.backend.issuegroup.repository.IssueGroupRepository;
import com.team33.backend.member.domain.Member;
import com.team33.backend.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class IssueRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private IssueGroupRepository issueGroupRepository;

    @Autowired
    private IssueRepository issueRepository;

    @BeforeEach
    void setUp() {
        issueRepository.deleteAll();
        memberRepository.deleteAll();
        issueGroupRepository.deleteAll();
    }

    @Nested
    @DisplayName("이슈개수 조회는")
    class CountByIssueStatusTest {

        @Test
        void 열린_이슈가_없을때_열린이슈개수는_0_이다() {
            // given
            int expectedCount = 0;

            // when
            int openIssueCount = issueRepository.countByIssueStatus(IssueStatus.OPEN);

            // then
            assertThat(openIssueCount).isEqualTo(expectedCount);
        }

        @Test
        void 닫힌_이슈가_없을때_닫힌이슈개수는_0_이다() {
            // given
            int expectedCount = 0;

            // when
            int openIssueCount = issueRepository.countByIssueStatus(IssueStatus.CLOSED);

            // then
            assertThat(openIssueCount).isEqualTo(expectedCount);
        }

        @Test
        void 열린_이슈가_1개_있으면_열린이슈개수개수는_1_이다() {
            // given
            saveNewIssue(IssueStatus.OPEN);
            int expectedCount = 1;

            // when
            int openIssueCount = issueRepository.countByIssueStatus(IssueStatus.OPEN);

            // then
            assertThat(openIssueCount).isEqualTo(expectedCount);
        }

        @Test
        void 닫힌_이슈가_1개_있으면_닫힌이슈개수는_1_이다() {
            // given
            saveNewIssue(IssueStatus.CLOSED);
            int expectedCount = 1;

            // when
            int openIssueCount = issueRepository.countByIssueStatus(IssueStatus.CLOSED);

            // then
            assertThat(openIssueCount).isEqualTo(expectedCount);
        }
    }

    @Nested
    @DisplayName("이슈 상태에 따른 이슈 조회는")
    class FindAllByIssueStatusTest {

        @Test
        void 열린_이슈_조회시_열린_이슈가_1개_있으면_1개인_리스트를_반환한다() {
            // given
            int expectedSize = 1;
            IssueStatus status = IssueStatus.OPEN;
            Issue savedIssue = saveNewIssue(status);

            // when
            List<Issue> issues = issueRepository.findAllByIssueStatus(status, Pageable.unpaged());

            // then
            assertThat(issues).isNotNull();
            assertThat(issues).isNotEmpty();
            assertThat(issues).size().isEqualTo(expectedSize);
            for (Issue issue : issues) {
                assertIssue(issue, savedIssue);
            }
        }

        @Test
        void 닫힌_이슈_조회시_닫힌_이슈가_1개_있으면_1개인_리스트를_반환한다() {
            // given
            int expectedSize = 1;
            IssueStatus status = IssueStatus.CLOSED;
            Issue savedIssue = saveNewIssue(status);

            // when
            List<Issue> issues = issueRepository.findAllByIssueStatus(status, Pageable.unpaged());

            // then
            assertThat(issues).isNotNull();
            assertThat(issues).isNotEmpty();
            assertThat(issues).size().isEqualTo(expectedSize);
            for (Issue issue : issues) {
                assertIssue(issue, savedIssue);
            }
        }

        @Test
        void 이슈가_없으면_빈_리스트를_반환한다() {
            // when
            List<Issue> issues = issueRepository.findAllByIssueStatus(IssueStatus.OPEN, Pageable.unpaged());

            // then
            assertThat(issues).isNotNull();
            assertThat(issues).isEmpty();
        }
    }

    private Issue saveNewIssue(IssueStatus issueStatus) {
        Member member = memberRepository.save(new Member("jay", "jinan159", "https://profile.image.url"));
        IssueGroup group = issueGroupRepository.save(new IssueGroup("default"));

        Issue issue = new Issue("안녕하세요", member, group);

        if (IssueStatus.CLOSED.equals(issueStatus)) {
            issue.close();
        }

        return issueRepository.save(issue);
    }

    private void assertIssue(Issue issue, Issue savedIssue) {
        assertThat(issue.getId()).isEqualTo(savedIssue.getId());
        assertThat(issue.getTitle()).isEqualTo(savedIssue.getTitle());
        assertThat(issue.getIssueStatus()).isEqualTo(savedIssue.getIssueStatus());
        assertThat(issue.getAuthor()).isNotNull();
        assertThat(issue.getAuthor().getId()).isEqualTo(savedIssue.getAuthor().getId());
        assertThat(issue.getAuthor().getName()).isEqualTo(savedIssue.getAuthor().getName());
        assertThat(issue.getAuthor().getProfileImageUrl()).isEqualTo(savedIssue.getAuthor().getProfileImageUrl());
        assertThat(issue.getIssueGroup()).isNotNull();
        assertThat(issue.getIssueGroup().getId()).isEqualTo(savedIssue.getIssueGroup().getId());
        assertThat(issue.getIssueGroup().getName()).isEqualTo(savedIssue.getIssueGroup().getName());
    }
}