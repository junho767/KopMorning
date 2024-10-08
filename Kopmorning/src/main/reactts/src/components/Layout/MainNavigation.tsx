import { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import classes from '../css/MainNavigation.module.css';
import AuthContext from '../../store/auth-context';

const MainNavigation = () => {
    const authCtx = useContext(AuthContext);
    const [nickname, setNickname] = useState('');
    let isLogin = authCtx.isLoggedIn;
    let isGet = authCtx.isGetSuccess;

    const callback = (str:string) => {
        setNickname(str);
    }

    useEffect(() => {
        if(isLogin) {
            console.log('로그인 시작');
            authCtx.getUser();
        }
    }, [isLogin]); // 의존성 배열 [isLogin]

    useEffect(() => {
        if(isGet) {
            console.log('로그인 성공');
            callback(authCtx.userObj.nickname);
        }
    }, [isGet]);

    const toggleLogoutHandler = () => {
        authCtx.logout();
    }

    return(
        <header className = {classes.header}>
            <Link to ='/'><div className={classes.logo}> 메인화면 </div></Link>
            <Link to ='/page/1'><div className={classes.articleLink}>게시판</div></Link>
            <nav>
                <ul>
                    <li>{!isLogin && <Link to='/login'>로그인</Link>}</li>
                    <li>{!isLogin && <Link to='/signup'>회원가입</Link>}</li>
                    <li>{isLogin && <Link to='/profile'>{nickname}</Link>}</li>
                    <li>{isLogin && <button onClick={toggleLogoutHandler}>로그아웃</button>}</li>
                </ul>
            </nav>
        </header>
    );
}

export default MainNavigation;