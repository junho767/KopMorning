import React from "react";
import { Pagination } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';
import classes from '../css/Paging.module.css';

type Props = { currentPage:number, maxPage:number }

const Paging:React.FC<Props> = (props) =>{
    let navigate = useNavigate();

    const maxNum = props.maxPage;
    const curNum = props.currentPage;

    const navigateToPage = (page : number) => (event:React.MouseEvent<HTMLElement>) => {
        event.preventDefault();
        console.log(page);
        // 현재 페이지를 클릭한다면, 이동할 수 없게 if 값으로 설정을 해주었다.
        if(props.currentPage !== page){
            const pageNum = String(page);
            navigate(`../page/${pageNum}`);
        }
    };

    const definePage = () => {
        let pageProps: JSX.Element[] = [];
        if(maxNum < 6){
            for(let num = 1; num<=maxNum; num++){
                pageProps.push(
                //active 속성: 현재 페이지가 활성화된 페이지임을 나타냅니다.
                    <Pagination.Item key = {num} active= {num === curNum} onClick={navigateToPage(num)}>
                        {num}
                    </Pagination.Item>
                )
            }
            return pageProps;
        }

        if(curNum < 5){
            for(let num= 1; num<=4 ; num++){
                pageProps.push(
                    <Pagination.Item key = {num} active= {num === curNum} onClick={navigateToPage(num)}>
                        {num}
                    </Pagination.Item>
                )
            }
            pageProps.push(<Pagination.Ellipsis/>);
            pageProps.push(<Pagination.Item>{maxNum}</Pagination.Item>);
            pageProps.push(<Pagination.Next/>);
            return pageProps;
        }

        if (maxNum - curNum < 4) {
            pageProps.push(<Pagination.First />)
            pageProps.push(<Pagination.Item>{1}</Pagination.Item>);
            pageProps.push(<Pagination.Ellipsis />);
            for (let num = maxNum-3; num <= maxNum; num ++) {
                pageProps.push(
                <Pagination.Item key={num} active={num === curNum} onClick={navigateToPage(num)}>
                    {num}
                </Pagination.Item>
                )
            }
            return pageProps;
        }
        pageProps.push(<Pagination.First />)
        pageProps.push(<Pagination.Item>{1}</Pagination.Item>);
        pageProps.push(<Pagination.Ellipsis />);
        for (let num = curNum-2; num <= curNum + 2; num++) {
          <Pagination.Item key={num} active={num === curNum} onClick={navigateToPage(num)}>
            {num}
          </Pagination.Item>
        }
        return pageProps;
    }
    return (
        <Pagination className={classes.page}>
            {definePage()}
        </Pagination>
    );
}
export default Paging;