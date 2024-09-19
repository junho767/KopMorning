import { GET, POST }  from "./fetch-auth-action";

// 토큰 생성 함수, auth-action 에서만 사용 가능
const createTokenHeader = (token:string) => {
  return {
    headers: {
      'Authorization': 'Bearer ' + token
    }
  }
}
// 토큰의 만료시간을 계싼하는 함수, auth-action 에서만 사용 가능
const calculateRemainingTime = (expirationTime:number) => {
  const currentTime = new Date().getTime();
  const adjExpirationTime = new Date(expirationTime).getTime();
  const remainingDuration = adjExpirationTime - currentTime;
  return remainingDuration;
};

// 토큰값과 만료시간을 부여 받으면 받은 값을 LocalStorage 에 저장하는 함수, 이 후 남은 시간 반환
export const loginTokenHandler = (token:string, expirationTime:number) => {
  localStorage.setItem('token', token);
  localStorage.setItem('expirationTime', String(expirationTime));
  const remainingTime = calculateRemainingTime(expirationTime);
  return remainingTime;
}

// localStorage 에 토큰이 존재하는 지 검색하는 함수, 존재한다면 만료까지 남은 시간과 토큰값을 같이 반환
// 만약 만료 시간이 1초 아래로 남았다면 자동으로 토큰을 삭제 함.
export const retrieveStoredToken = () => {
  const storedToken = localStorage.getItem('token');
  const storedExpirationDate = localStorage.getItem('expirationTime') || '0';

  const remaingTime = calculateRemainingTime(+ storedExpirationDate);

  if(remaingTime <= 1000) {
    localStorage.removeItem('token');
    localStorage.removeItem('expirationTime');
    return null
  }

  return {
    token: storedToken,
    duration: remaingTime
  }
}

// 회원가입 URL, POST 방식으로 호출하는 함수이다.
export const signupActionHandler = (email: string, password: string, nickname: string) => {
  const URL = '/auth/signup'
  const signupObject = { email, password, nickname };

  const response = POST(URL, signupObject, {});
  return response;
};


// 로그인 URL 을 POST 방식으로 호출하는 함수이다.
export const loginActionHandler = (email:string, password: string) => {
  const URL = '/auth/login';
  const loginObject = { email, password };
  const response = POST(URL, loginObject, {});
  return response;
};

// 로그아웃 시 발생하는 함수이며, LocalStorage 에 저장된 토큰과 만료시간을 제거.
export const logoutActionHandler = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('expirationTime');
};

// 유저의 정보를 GET 방식으로 호출
export const getUserActionHandler = (token:string) => {
  const URL = '/member/me';
  const response = GET(URL, createTokenHeader(token));
  return response;
}
// 사용자가 닉네임을 수정했을 때 발생하는 액션으로 POST 방식으로 호출
export const changeNicknameActionHandler = ( nickname:string, token: string) => {
  const URL = '/member/nickname';
  const changeNicknameObj = { nickname };
  const response = POST(URL, changeNicknameObj, createTokenHeader(token));

  return response;
}
// 비밀번호 변경 시 발생하는 메서드로 POST 방식으로 호출
export const changePasswordActionHandler = (
  exPassword: string,
  newPassword: string,
  token: string
) => {
  const URL = '/member/password';
  const changePasswordObj = { exPassword, newPassword }
  const response = POST(URL, changePasswordObj, createTokenHeader(token));
  return response;
}