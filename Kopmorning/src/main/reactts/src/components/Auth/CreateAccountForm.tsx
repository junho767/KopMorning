import React, { useState, useRef, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../store/auth-context';
import classes from '../css/CreateAccountForm.module.css';

const CreateAccountForm = () => {
    // React Router 의 훅으로, 페이지 이동(네비게이션)을 처리합니다. 회원가입 성공 후 사용자를 다른 페이지로 이동시키는 역할을 합니다.
    let navigate = useNavigate();
    const authCtx = useContext(AuthContext);
    // Error 메세지
    const [errorMessage, setErrorMessage] = useState('');
    // useRef 는 DOM 요소에 직접 접근할 수 있게 해줍니다.
    const emailInputRef = useRef<HTMLInputElement>(null);
    const passwordInputRef = useRef<HTMLInputElement>(null);
    const passwordConfirmInputRef = useRef<HTMLInputElement>(null);
    const nicknameInputRef = useRef<HTMLInputElement>(null);

    const submitHandler = (event: React.FormEvent) => {
        // 폼이 제출될 때 발생하는 기본 동작(페이지 새로고침)을 막습니다.
        event.preventDefault();

        const enteredEmail = emailInputRef.current!.value;
        const enteredPassword = passwordInputRef.current!.value;
        const enteredPasswordConfirm = passwordConfirmInputRef.current!.value;
        const enteredNickname = nicknameInputRef.current!.value;

        if(enteredPassword.length < 8){
            setErrorMessage('비밀번호가 8자 이상이어야 합니다.');
            return;
        }
        if(enteredPassword != enteredPasswordConfirm){
            setErrorMessage('비밀번호가 서로 다릅니다.');
            return;
        }
        setErrorMessage('');

        authCtx.signup(enteredEmail, enteredPassword, enteredNickname);

        // replace: true 는 히스토리 기록을 대체하여, "뒤로 가기"를 눌렀을 때 다시 회원가입 폼으로 돌아오지 않게 만듭니다.
        if(authCtx.isSuccess){
            return navigate("/", {replace : true});
        }
    }

    return (
        <section className = {classes.auth}>
            <h1>계정 생성</h1>
            <form onSubmit = {submitHandler}>
                <div className = {classes.control}>
                    <label htmlFor = 'email'>이메일 : </label>
                    <input type = 'email' id='email' required ref = {emailInputRef}/>
                </div>
                <div className = {classes.control}>
                    <label htmlFor = 'password'>비밀번호 : </label>
                    <input type = 'password' id='password' required ref = {passwordInputRef}/>
                </div>
                <div className = {classes.control}>
                    <label htmlFor = 'password'>비밀번호 확인: </label>
                    <input type = 'password' id='passwordConfirm' required ref = {passwordConfirmInputRef}/>
                </div>
                <div className = {classes.control}>
                    <label htmlFor = 'nickname'>닉네임 : </label>
                    <input type = 'text' id=' nickname' required ref = {nicknameInputRef}/>
                </div>
                {errorMessage && <p className={classes.error}>{errorMessage}</p>} {/* 오류 메시지 표시 */}
                <div className = {classes.control}>
                    <button type = 'submit'>완료</button>
                </div>
            </form>
        </section>
    );
};

export default CreateAccountForm;