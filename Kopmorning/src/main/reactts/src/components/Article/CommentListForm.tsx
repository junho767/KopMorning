import React, { useCallback, useContext, useEffect, useState, useRef } from 'react';
import AuthContext from '../../store/auth-context';
import CommentContext from '../../store/comment-context';
import Comment from './CommentForm';
import classes from '../css/CommentList.module.css';

type Props = { item:string | undefined }

type CommentInfo = {
  commentId: number,
  memberNickname: string,
  commentBody: string,
  createdAt: Date,
  written: boolean
  onDelete?: (id:string) => void;
}

type PostComment = {
  articleId: string,
  body: string
}



const CommentList:React.FC<Props> = (props) => {

  const [comments, setComments] = useState<CommentInfo[]>();
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const commentRef = useRef<HTMLTextAreaElement>(null);

  const authCtx = useContext(AuthContext);
  const commentCtx = useContext(CommentContext);

  let isLogin = authCtx.isLoggedIn;
  let isSuccess = commentCtx.isSuccess;
  const token = authCtx.token;
  const articleId = String(props.item);

  const getContext = useCallback(() => {
    setIsLoading(false);
    (isLogin ? commentCtx.getComments(articleId, authCtx.token) : commentCtx.getComments(articleId));
    console.log("get comment");
  }, [isLogin]);

  useEffect(() => {
    getContext();
  }, [getContext]);

  useEffect(() => {
    if (isSuccess) {
      setComments(commentCtx.commentList);
      console.log(comments);
      setIsLoading(true);
    }
  }, [isSuccess]);

  const createComment = (event:React.FormEvent) => {
    event.preventDefault();
    const comment:PostComment =
    {
      articleId: articleId,
      body:commentRef.current!.value
    }

    commentCtx.postComment(comment, token);
  };

  const deleteComment = (commentId:string) => {
    commentCtx.deleteComment(commentId, articleId, token);
  }

  let media = <h3>is Loading...</h3>

  if (isLoading && comments) {
    if (comments!.length > 0) {
      console.log("if start")
      console.log(comments);
      media = (<ul>
        {
        comments.map((comment) => {
          return <Comment
            key={comment.commentId}
            commentId={comment.commentId}
            memberNickname={comment.memberNickname}
            commentBody={comment.commentBody}
            createdAt={comment.createdAt.toString()}
            written={comment.written}
            onDelete={deleteComment}
          />}
        )
      }
    </ul>)
    } else {
      media = <div></div>
    }


  }

  return (
    <div className={classes.list}>
      {media}
      {isLogin &&
      <form onSubmit={createComment} className={classes.box}>
        <label htmlFor="inputName" className={classes.name}>{authCtx.userObj.nickname}</label>
        <textarea
          name="comment"
          className={classes.text}
          cols={100}
          rows={3}
          ref={commentRef}/>
        <input type="submit" className={classes.btn}/>
      </form>}
    </div>
  );
}

export default CommentList;