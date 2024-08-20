import React, {useEffect, useState} from 'react';
import axios from 'axios';

function App() {
    const [hello, setHello] = useState(''); // 서버에서 받은 헬로 데이터를 저장할 상태 변수
    const [check, setCheck] = useState(''); // 서버에서 받은 체크 데이터를 저장할 상태 변수
    const [error, setError] = useState(''); // 오류 메시지를 저장할 상태 변수
    const [loading, setLoading] = useState(true); // 로딩 상태를 관리할 상태 변수

    useEffect(() => {
        // 비동기 함수 정의
        const fetchData = async () => {
        try{
            // 두 API 호출을 병렬로 수행
            const [helloResponse, checkResponse] = await Promise.all([
                axios.get('/api/hello'),
                axios.get('/api/check'),
            ]);
            // 각 응답 데이터를 상태 변수에 저장
            setHello(helloResponse.data);
            setCheck(checkResponse.data);
            setLoading(false); // 데이터 로드 완료
        }
        catch(err){
            setError('실패 ㅠㅠ');
            setLoading(false); // 데이터 로드 완료(오류 발생)
            console.log(err);
        }};
        fetchData();
    }, []);
    if(loading){
        return <div>Loading....!</div>;
    }
    return (
        <div>
              {error && <p style={{ color: 'red' }}>{error}</p>} {/* 오류 메시지 표시 */}
              <p>백엔드에서 가져온 /api/hello 데이터입니다: {hello}</p> {/* /api/hello 응답 데이터 표시 */}
              <p>백엔드에서 가져온 /api/check 데이터입니다: {check}</p> {/* /api/check 응답 데이터 표시 */}
        </div>
    );
}

export default App;
