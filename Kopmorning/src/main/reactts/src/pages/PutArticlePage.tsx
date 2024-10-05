import {useParams} from "react-router-dom";
import CreateArticleForm from "../components/Article/CreateArticleForm";
import { ArticleContextProvider} from "../store/article-context";

const CreateArticlePage = () =>{
    let { articleId } = useParams();
    return (
        <ArticleContextProvider>
            <CreateArticleForm item={undefined}/>
        </ArticleContextProvider>
    );
}

export default CreateArticlePage;