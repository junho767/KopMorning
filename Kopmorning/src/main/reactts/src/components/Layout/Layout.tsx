// Layout 컴포넌트를 정의
// 주로 다른 컴포넌트나 페이지를 감싸는 역할
import React from "react";

import { Fragment } from "react";
import MainNavigation from "./MainNavigation";

// React.ReactNode 는 JSX, 문자열, 숫자, 배열, null, undefined 등
// React 에서 렌더링할 수 있는 모든 타입을 포함
type Props = {
    children ? : React.ReactNode
}
// <main> 태그 안에 자식 컴포넌트들이 렌더링되어, 각 페이지의 주요 콘텐츠가 표시됩니다.
const Layout: React.FC<Props> = (Props) => {
    return(
        <Fragment>
            <MainNavigation/>
            <main>{Props.children}</main>
        </Fragment>
    )
};

export default Layout;