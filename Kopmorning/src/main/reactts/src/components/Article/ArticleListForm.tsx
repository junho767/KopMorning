import BootStrapTable from 'react-bootstrap-table-next';
import { Button } from 'react-bootstrap';
import { useCallback, useContext, useEffect, useState } from 'react';
import AuthContext from '../../store/auth-context';
import { Link, useNavigate } from 'react-router-dom';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import classes from '../css/ArticleList.module.css';
import ArticleContext from '../../store/article-context';
import Paging from './Paging';

type Props = { item:string | undefined }

type ArticleInfo = {
  articleId: number,
  memberNickname: string,
  articleTitle: string,
  articleBody: string,
  cratedAt: string,
  updatedAt?: string,
  isWritten?: boolean
};


const ArticleList:React.FC<Props> = (props) => {

  let navigate = useNavigate();
  const pageId = String(props.item);

  const columns = [{
    dataField: 'articleId',
    text: '글 번호',
    headerStyle: () => {
      return { width: "10%" };
    }
  },{
    dataField: 'articleTitle',
    text: '제목',
    headerStyle: () => {
      return { width: "65%" };
    },
    events: {
      onClick: (e:any, column:any, columnIndex:any, row:any, rowIndex:any) => {
        const articleIdNum:string = row.articleId;
        navigate(`../article/${articleIdNum}`);
      }
    }
  },{
    dataField: 'memberNickname',
    text: '닉네임',
    headerStyle: () => {
      return { width: "20%" };
    }
  },{
    dataField: 'createdAt',
    text: '작성일',
    headerStyle: () => {
      return { width: "20%" };
    }
  },]

   // 글 작성 여부
  const authCtx = useContext(AuthContext);
  // 게시물 관련 정보
  const articleCtx = useContext(ArticleContext);

  // 페이지 게시물 리스트 저장 !
  const [AList, setAList] = useState<ArticleInfo[]>([]);
  // 전체 페이지 숫자
  const [maxNum, setMaxNum] = useState<number>(1);

  let isLogin = authCtx.isLoggedIn;
    // [] 의존성 배열 : 이 배열에 지정된 값이 변경될 때만 함수 실행
    // 이 메서드는 비어있기 때문에 처음 마운트될 때 딱 한 번 생성 즉, 그 이후에는 동인한 참조를 유지함.
  const fetchListHandler = useCallback(() => {
    articleCtx.getPageList(pageId);
  }, []);


  useEffect(() => {
    fetchListHandler();
  }, [fetchListHandler]);


  useEffect(() => {
    if (articleCtx.isSuccess) {
      setAList(articleCtx.page);
      console.log(AList);
      setMaxNum(articleCtx.totalPages);
    }
  }, [articleCtx])

  return (
    <div className={classes.list}>
      <BootStrapTable keyField='id' data = { AList } columns={ columns } />
      <div>{isLogin &&
        <Link to="/postArticle">
          <Button>글 작성</Button>
        </Link>
      }
      </div>
      <Paging currentPage={Number(pageId)} maxPage={maxNum}/>
    </div>
  );
}
export default ArticleList;