import React, {useState, useEffect, useCallback, useRef} from "react";
import * as articleAction from './article-action';

type Props = {children? : React.ReactNode}
type ArticleInfo = {
    articleId : number;
    memberNickname : string,
    title : string,
    body : string,
    createdAt : string,
    updatedAt? : string,
    isWritten? : boolean
};

interface PostArticle{
    id? : string,
    title : string,
    body : string
}

interface Ctx{
    article? : ArticleInfo | undefined;
    page : ArticleInfo[];
    isSuccess : boolean;
    isGetUpdateSuccess : boolean;
    totalPages : number;
    getPageList : (pageId : string) => void;
    getOneArticle : (param : string, token? : string) => void;
    createArticle : (article : PostArticle, token : string) => void;
    changeArticle : (param : string, token : string) => void;
    putArticle : (article : PostArticle, token : string) => void;
    deleteArticle : (param : string, token : string) => void;
}

const ArticleContext = React.createContext<Ctx>({
  article: undefined,
  page: [],
  isSuccess: false,
  isGetUpdateSuccess: false,
  totalPages: 0,
  getPageList: () => {},
  getOneArticle: () => {},
  createArticle:  () => {},
  changeArticle: () => {},
  putArticle: () => {},
  deleteArticle: () => {}
});

export const ArticleContextProvider:React.FC<Props> = (props) => {
    const [article, setArticle] = useState<ArticleInfo>();
    const [page, setPage] = useState<ArticleInfo[]>([]);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [isSuccess, setIsSuccess] = useState<boolean>(false);
    const [isGetUpdateSuccess, setIsGetUpdateSuccess] = useState<boolean>(false);

    const getPageHandlerV2 = async (PageId : string) => {
        setIsSuccess(false);
        const data = await articleAction.getPageList(PageId);
        const page:ArticleInfo[] = data?.data.content;
        const pages:number = data?.data.totalPages;
        setPage(page);
        setTotalPages(pages);
        setIsSuccess(true);
    }

    const getArticleHandler = (param : string, token? : string) => {
        setIsSuccess(false);
        const data = (token ?
         articleAction.getOneArticle(param, token)
         : articleAction.getOneArticle(param));
        data.then((result) => {
            if(result !== null){
                const article:ArticleInfo = result.data;
                setArticle(article);
            }
        })
        setIsSuccess(true);
    }

    const createArticleHandler = (article : PostArticle, token : string) => {
        setIsSuccess(false);
        const data = articleAction.createArticle(article, token);
        data.then((result) => {
            if(result !== null){
                console.log("Create Article : "+ isSuccess);
            }
        })
        setIsSuccess(true);
    }

    const changeArticleHandler = async (param : string, token : string) => {
        setIsGetUpdateSuccess(false);
        const updateData = await articleAction.changeArticle(param, token);
        const article : ArticleInfo = updateData?.data;
        setArticle(article);
        setIsGetUpdateSuccess(true);
    }

    const putArticleHandler = (article : PostArticle, token : string) => {
        setIsSuccess(false);
        const data = articleAction.putArticle(article, token);
        data.then((result)=>{
            if(result !== null){
                console.log("Update Success");
            }
        })
        setIsSuccess(true);
    }

    const deleteArticleHandler = (param : string, token : string) =>{
        setIsSuccess(false);
        const data = articleAction.deleteArticle(param, token);
        data.then((result) => {
            if (result !== null) {
                console.log("Delete Complete");
            }
        })
        setIsSuccess(true);
    }

    const contextValue:Ctx = {
        article,
        page,
        isSuccess,
        isGetUpdateSuccess,
        totalPages,
        getPageList: getPageHandlerV2,
        getOneArticle: getArticleHandler,
        createArticle: createArticleHandler,
        changeArticle: changeArticleHandler,
        putArticle: putArticleHandler,
        deleteArticle: deleteArticleHandler
      }
    return (
        <ArticleContext.Provider value={contextValue}>
            {props.children}
        </ArticleContext.Provider>)
}
export default ArticleContext;