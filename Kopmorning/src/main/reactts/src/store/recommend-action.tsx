import {GET, POST, PUT, DELETE} from "./fetch-action";

const createToken = (token : string) =>{
    return{
        header:{
            'Authorization': 'Bearer' + token
        }
    }
}

export const getRecommends = (param : string, token?:string) => {
    const URL = '/recommend/list?id=' + param;
    const response = (token ? GET(URL, createToken(token)) : GET(URL, {}));
    return response;
}

export const postRecommend = async (id_str : string, token : string) => {
    const URL = '/recommend/';
    const id = +id_str;
    const response = POST(URL, {id : id}, createToken(token));
}

export const deleteRecommend = (param : string, token : string) =>{
    const URL = '/recommend/remove?id=' + param;
    const response = DELETE(URL, createToken(token));
}

export {}