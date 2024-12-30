package com.uplift.service;

import com.uplift.model.Member;

import java.util.List;

public interface MemberService {
    Member saveMember(Member member);

    Member getMemberById(String id);

    List<Member> getAllMembers();

    void deleteMember(String id);

    Member updateMember(String id, Member member);
}
