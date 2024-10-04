import { Fragment } from 'react';
import { useParams } from 'react-router-dom';
import ArticleOne from "../components/Article/ArticleOneForm";
import Comment from "../components/Article/CommentListForm";
import Recommend from "../components/Article/RecommendForm";
import { ArticleContextProvider } from "../store/article-context";
import { CommentContextProvider } from "../store/comment-context";
import { RecommendContextProvider } from "../store/recommend-context";

const ArticleOnePage = () =>{
    let {articleId} = useParams();
    return (
        <Fragment>
            <ArticleContextProvider>
                <ArticleOne item={articleId}/>
            </ArticleContextProvider>
            <RecommendContextProvider>
                <Recommend item={articleId}/>
            </RecommendContextProvider>
            <CommentContextProvider>
                <Comment item={articleId}/>
            </CommentContextProvider>
        </Fragment>
    );
}

export default ArticleOnePage;