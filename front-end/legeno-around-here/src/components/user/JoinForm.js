import React, { useState, useCallback, useMemo } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import styled from "styled-components";

import Input from "./Input";

const FrameStyle = styled.div`
  width: 360px;
  height: 640px;
  margin: 0 auto;
  border: 1px solid #bcbcbc;
`;

const FontStyle = styled.div`
  width: 340px;
  height: 18px;
  font-family: NotoSansCJKkr;
  font-size: 12px;
  font-weight: 500;
  font-stretch: normal;
  font-style: normal;
  line-height: normal;
  letter-spacing: normal;
  color: #3f3f3f;
  font-weight: bold;
`;

const HeaderStyle = styled.div`
  width: 340px;
  height: 36px;
  font-family: NotoSansCJKkr;
  font-size: 24px;
  font-weight: bold;
  font-stretch: normal;
  font-style: normal;
  line-height: normal;
  letter-spacing: normal;
  color: #222222;
`;

const CheckStyle = styled.p`
  width: 320px;
  height: 18px;
  font-size: 10px;
  color: red;
  line-height: 18px;
  margin: 0px 0px;
`;

const ButtonStyle = styled.button`
  width: 320px;
  height: 40px;
  font-size: 16px;
  font-weight: bold;
  line-height: 40px;
  background-color: #bcbcbc;
  border: 0;
  outline: 0;
  margin: 5px auto;
`;

const WrapperStyle = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

function JoinForm() {
  const NICKNAME_MIN_LENGTH = 1;
  const NICKNAME_MAX_LENGTH = 10;
  const PASSWORD_MIN_LENGTH = 8;
  const PASSWORD_MAX_LENGTH = 16;
  const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeat, setPasswordRepeat] = useState("");

  const validateEmail = useMemo(() => {
    return email && !EMAIL_REGEX.test(String(email).toLowerCase());
  }, [EMAIL_REGEX, email]);

  const validateNickname = useMemo(() => {
    return (
      nickname && (
      nickname.length < NICKNAME_MIN_LENGTH ||
      nickname.length > NICKNAME_MAX_LENGTH)  
    );
  }, [nickname]);

  const validatePassword = useMemo(() => {
    return (
      password && (
      password.length < PASSWORD_MIN_LENGTH ||
      password.length > PASSWORD_MAX_LENGTH)
    );
  }, [password]);

  const validatePasswordRepeat = useMemo(() => {
    return passwordRepeat && (passwordRepeat.length === 0 || password !== passwordRepeat);
  }, [password, passwordRepeat]);

  const emailCheck = useMemo(() => {
    if (validateEmail) {
      return (
        <CheckStyle className="alert">
          올바른 이메일 형식을 입력해주세요.
        </CheckStyle>
      );
    }
    return <CheckStyle className="alert"></CheckStyle>;
  }, [validateEmail]);

  const nicknameCheck = useMemo(() => {
    if (validateNickname) {
      return (
        <CheckStyle className="alert">
          닉네임 길이는 10 자 이하로 해주세요.
        </CheckStyle>
      );
    }
    return <CheckStyle className="alert"></CheckStyle>;
  }, [validateNickname]);

  const passwordCheck = useMemo(() => {
    if (validatePassword) {
      return (
        <CheckStyle className="alert">
          비밀번호 길이는 8 ~ 16 자로 해주세요.
        </CheckStyle>
      );
    }
    return <CheckStyle className="alert"></CheckStyle>;
  }, [validatePassword]);

  const passwordRepeatCheck = useMemo(() => {
    if (validatePasswordRepeat) {
      return (
        <CheckStyle className="alert">비밀번호가 일치하지 않습니다.</CheckStyle>
      );
    }
    return <CheckStyle className="alert"></CheckStyle>;
  }, [validatePasswordRepeat]);

  const handleChangeEmail = useCallback(({ target: { value } }) => {
    setEmail(value);
  }, []);

  const handleChangeNickname = useCallback(({ target: { value } }) => {
    setNickname(value);
  }, []);

  const handleChangePassword = useCallback(({ target: { value } }) => {
    setPassword(value);
  }, []);

  const handleChangePasswordRepeat = useCallback(({ target: { value } }) => {
    setPasswordRepeat(value);
  }, []);

  const handleReset = useCallback(() => {
    setEmail("");
    setNickname("");
    setPassword("");
    setPasswordRepeat("");
  }, []);

  const join = useCallback(() => {
    axios
      .post("http://localhost:8080/join", {
        email,
        nickname,
        password,
      })
      .then((response) => {
        alert("회원가입을 축하드립니다.");
        document.location.href = "/login";
      })
      .catch(() => {
        alert("회원가입에 실패하였습니다.");
        handleReset();
      });
  }, [email, nickname, password, handleReset]);

  const handleSubmit = useCallback(
    (event) => {
      event.preventDefault();
      if (
        validateEmail ||
        validateNickname ||
        validatePassword ||
        validatePasswordRepeat
      ) {
        alert("입력값을 확인해 주세요.");
        return;
      }
      join();
    },
    [
      validateEmail,
      validateNickname,
      validatePassword,
      validatePasswordRepeat,
      join,
    ]
  );

  return (
    <FrameStyle>
      <WrapperStyle>
        <HeaderStyle>우리동네 캡짱</HeaderStyle>
      </WrapperStyle>
      <form onSubmit={handleSubmit}>
        <WrapperStyle>
          <FontStyle>아이디 (E-mail)</FontStyle>
        </WrapperStyle>
        <Input
          type="email"
          placeholder="이메일"
          value={email}
          onChange={handleChangeEmail}
        />
        {emailCheck}
        <WrapperStyle>
          <FontStyle>닉네임</FontStyle>
        </WrapperStyle>
        <Input
          type="text"
          placeholder="닉네임"
          value={nickname}
          onChange={handleChangeNickname}
        />
        {nicknameCheck}
        <WrapperStyle>
          <FontStyle>비밀번호</FontStyle>
        </WrapperStyle>
        <Input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={handleChangePassword}
        />
        {passwordCheck}
        <Input
          type="password"
          placeholder="비밀번호 확인"
          value={passwordRepeat}
          onChange={handleChangePasswordRepeat}
        />
        {passwordRepeatCheck}
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <br></br>
        <WrapperStyle>
          <ButtonStyle type="submit">회원가입</ButtonStyle>
        </WrapperStyle>
        <Link to="/" style={{ textDecoration: "none" }}>
          <WrapperStyle>
            <ButtonStyle type="button">홈으로</ButtonStyle>
          </WrapperStyle>
        </Link>
      </form>
    </FrameStyle>
  );
}

export default JoinForm;
