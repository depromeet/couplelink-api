package com.depromeet.couplelink.entity;

import org.junit.Test;

public class MemberDetailTest {
    private final MemberDetail memberDetail = new MemberDetail();

    @Test
    public void parseBirthDate() {
        // given
        String birthDate = "2019-07-26";
        // when
        memberDetail.parseBirthDate(birthDate);
        // then
        System.out.println(memberDetail.getBirthDate());
    }

}