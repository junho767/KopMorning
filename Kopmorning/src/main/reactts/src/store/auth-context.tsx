import React, { useState, useEffect, useCallback } from "react";
// auth-action 에서 exported(내보낸) 항목을 모두 가져온다. 이름을 authAction 으로!
import * as authAction from '../auth/auth-action';

let logoutTimer : NodeJS.Timeout;

type Props = { children ? : React.ReactNode }
type UserInfo = { email : string, nickname : string };
type LoginToken = {
    grantType : string,
    accessToken : string,
    tokenExpiresIn : number
}

const AuthContext = React.createContext({
    token : '',
    userObj : { email : '', nickname : ''},
    isLoggedIn : false,
    isSuccess : false,
    isGetSuccess : false,
    signup : ( email : string, password : string, nickname : string) => {},
    login : ( email : string, password : string) => {},
    logout : () => {},
    getUser : () => {},
    changeNickname : ( nickname : string ) => {},
    changePassword : ( prePassword : string, newPassword : string ) => {}
});


export const AuthContextProvider:React.FC<Props> = (Progs) => {
    const tokenData = authAction.retrieveStoredToken();

    let initialToken : any;
    if(tokenData) {
        initialToken = tokenData.token!;
    }

    const[token, setToken] = useState(initialToken);
    const[userObj, setUserObj] = useState({
        email : '',
        nickname : ''
    });
    const[isSuccess , setIsSuccess] = useState<boolean>(false);
    const[isGetSuccess , setIsGetSuccess] = useState<boolean>(false);
    const userIsLoggedIn = !!token;

    const signupHandler = ( email : string, password : string, nickname : string ) => {
        setIsSuccess(false);
        const response = authAction.signupActionHandler( email, password, nickname );
        response.then((result) => {
            if(result !== null){
                setIsSuccess(true);
            }
        })
    };

    const loginHandler = ( email : string, password : string) => {
        setIsSuccess(false);

        const data = authAction.loginActionHandler(email, password);
        data.then((result) => {
            if(result !== null) {
                const loginData:LoginToken = result.data;
                setToken(loginData.accessToken);
                logoutTimer = setTimeout(
                    logoutHandler,
                    authAction.loginTokenHandler(loginData.accessToken, loginData.tokenExpiresIn)
                );
                setIsSuccess(true);
                console.log(isSuccess);
            }   
        })
    };

    const logoutHandler = useCallback(() => {
        setToken('');
        authAction.logoutActionHandler();
        if(logoutTimer) {
            clearTimeout(logoutTimer);
        }
    }, []);

    const getUserHandler = () => {
        setIsGetSuccess(false);
        const data = authAction.getUserActionHandler(token);
        data.then((result) => {
            if(result !== null) {
                console.log('get user START!');
                const userData:UserInfo = result.data;
                setUserObj(userData);
                setIsGetSuccess(true);
            }
        })
    };

    const changeNicknameHandler = ( nickname : string ) => {
        setIsSuccess(false);

        const data = authAction.changeNicknameActionHandler( nickname, token );
        data.then((result)=> {
            if(result !== null) {
                const userData:UserInfo = result.data;
                setUserObj(userData);
                setIsSuccess(true);
            }
        })
    };

    const changePasswordHandler = (exPassword:string, newPassword: string) => {
        setIsSuccess(false);
        const data = authAction.changePasswordActionHandler(exPassword, newPassword, token);
        data.then((result) => {
            if (result !== null) {
                setIsSuccess(true);
                logoutHandler();
            }
        });
    };

    useEffect(() => {
        if(tokenData) {
            console.log(tokenData.duration);
            logoutTimer = setTimeout(logoutHandler, tokenData.duration);
        }
    }, [tokenData, logoutHandler]);

    const contextValue = {
        token,
        userObj,
        isLoggedIn: userIsLoggedIn,
        isSuccess,
        isGetSuccess,
        signup: signupHandler,
        login: loginHandler,
        logout: logoutHandler,
        getUser: getUserHandler,
        changeNickname: changeNicknameHandler,
        changePassword: changePasswordHandler
      }

    return(
        <AuthContext.Provider value={contextValue}>
            {Progs.children}
        </AuthContext.Provider>
    )
}

export default AuthContext;