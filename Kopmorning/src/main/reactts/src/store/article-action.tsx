import {GET, POST, PUT, DELETE} from "./fetch-action";

interface PostArticle{
    id? : string,
    title : string,
    body : string
}

const createToken = (token : string) =>{
    return{
        header:{
            'Authorization': 'Bearer' + token
        }
    }
}

export const getPageList = (param : string) =>{
    const URL = '/article/page?page='+param;
    const response = GET(URL, {});
    return response;
}

export const getOneArticle = (param : string, token?:string) => {
    const URL = '/article/one?id='+param;
    if(!token){
        const response = GET(URL,{});
        return response;
    } else {
        const response = GET(URL,createToken(token));
        return response;
    }
}

export const createArticle = (article: PostArticle, token:string) => {
    const URL = '/article/';
    const response = POST(URL, article, createToken(token));
    return response;
}

export const changeArticle = ( param : string, token : string) => {
    const URL = '/article/change?id=' + param;
    const response = GET(URL, createToken(token));
    return response;
}

export const putArticle = (article : PostArticle, token : string) => {
    const URL = '/article/';
    const response = PUT(URL, article, createToken(token));
    return response;
}

export const deleteArticle = (param : string, token : string) => {
    const URL = '/article/one?id=' + param;
    const response = DELETE(URL, createToken(token));
    return response;
}

export {}