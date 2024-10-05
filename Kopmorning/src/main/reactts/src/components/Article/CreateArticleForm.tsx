import React, { useCallback, useContext, useEffect, useRef, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import ArticleContext from "../../store/article-context";
import AuthContext from "../../store/auth-context";

type Props = { item:string | undefined }

interface PostArticle {
  id?: string,
  title: string,
  body: string
}

const CreateArticleForm:React.FC<Props> = (props) => {

  let navigate = useNavigate();

  const [changeArticle, setChangeArticle] = useState<PostArticle>({
    title: '',
    body: ''
  });

  const articleCtx = useContext(ArticleContext);
  const authCtx = useContext(AuthContext);

  const titleRef = useRef<HTMLInputElement>(null);
  const mainRef = useRef<HTMLTextAreaElement>(null);

  const submitHandler = (event: React.FormEvent) => {
    // 새로고침 방지
    event.preventDefault();

    let postArticle:PostArticle = {
        // current! 는 null 아님을 보장하는 단언 연산자
      title: titleRef.current!.value,
      body: mainRef.current!.value
    }

    if (props.item) {
      console.log('update!');
      // ... : 스프레드 연산자 객체 복사 및 추가할 때 사용함.
      postArticle = { ...postArticle, id:props.item }
    }
    props.item ? articleCtx.putArticle(postArticle, authCtx.token) : articleCtx.createArticle(postArticle, authCtx.token);
  }

  const cancelHandler = () => {
    navigate("/page/1"); // 취소 시 페이지 이동
  };
  const setChangeArticleHandler = useCallback(() => {
    if (articleCtx.isGetUpdateSuccess) {
      setChangeArticle({
        title: articleCtx.article!.articleTitle,
        body: articleCtx.article!.articleBody
      })
    }
  }, [articleCtx.isGetUpdateSuccess])

  useEffect(() => {
    if (props.item) {
      articleCtx.changeArticle(props.item, authCtx.token);
    }
  }, [props.item])

  useEffect(() => {
    console.log('update effect')
    setChangeArticleHandler();
  }, [setChangeArticleHandler])

  useEffect(() => {
    if (articleCtx.isSuccess) {
      console.log("wrting success");
      navigate("/page/1", { replace: true })
    }
  }, [articleCtx.isSuccess])

  return (
    <div>
        <Form onSubmit={submitHandler}>
          <Form.Group>
            <Form.Label>제목</Form.Label>
            <Form.Control
              type="text"
              placeholder="제목을 입력하세요"
              required
              ref={titleRef}
              defaultValue={changeArticle.title}
            />
          </Form.Group>
          <br />
          <Form.Group>
            <Form.Label>본문</Form.Label>
            <Form.Control
              as="textarea"
              rows={20}
              required
              ref={mainRef}
              defaultValue={changeArticle.body}
            />
          </Form.Group>
          <br />
          <Button variant="primary" onClick={cancelHandler}>
            취소
          </Button>
          <Button variant="primary" type="submit">
            작성
          </Button>
        </Form>
    </div>
  );
}

export default CreateArticleForm;