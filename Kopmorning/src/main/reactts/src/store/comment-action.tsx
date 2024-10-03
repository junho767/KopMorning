import {GET, POST, PUT, DELETE} from "./fetch-action";

interface postComment{
    articleId : string,
    body : string
}

const createToken = (token : string) =>{
    return{
        header:{
            'Authorization': 'Bearer' + token
        }
    }
}

export const getComments = (param : string, token?:string) => {
    const URL = '/comment/list?id=' + param;
    const response = (token ? GET(URL, createToken(token)) : GET(URL, {}));
    return response;
}

export const postComment = (comment : postComment, token : string) => {
    const URL = '/comment/';
    const response = POST(URL, comment, createToken(token));
    return response;
}

export const deleteComment = (param : string, token : string) => {
    const URL = '/comment/one?id=' + param;
    const response = DELETE(URL, createToken(token));
    return response;
}

export {}