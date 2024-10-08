import React, { useRef } from "react";
import classes from "../css/Comment.module.css";

type Props = {
  commentId: number,
  memberNickname: string,
  commentBody: string,
  createdAt: string,
  written: boolean,
  onDelete: (id:string) => void;
}

const Comment:React.FC<Props> = (props) => {

  const deleteIdRef = useRef<HTMLInputElement>(null);

  const submitDeleteHandler = (event:React.FormEvent) => {
    event.preventDefault();
    const deleteId = deleteIdRef.current!.value;
    props.onDelete(deleteId);
  };

  return (
    <li className={classes.list}>
      <h4 className={classes.name}>{props.memberNickname}</h4>
      <p className={classes.body}>{props.commentBody}</p>
      <p className={classes.date}>{props.createdAt}</p>
      <form onSubmit={submitDeleteHandler}>
        <input
          type="hidden"
          name="commentId"
          value={props.commentId}
          ref={deleteIdRef}
        />
        {props.written && <button type="submit">삭제</button>}
      </form>
    </li>
  )
}

export default Comment;