// GET, POST, PUT, DELETE 요청을 간편하게 호출
import axios, { AxiosError,AxiosResponse }  from 'axios';

type ServerError = { errorMessage: string };
type LoginFailType = { status: number, error: string,};

interface FetchData {
  method: string,
  url: string,
  data? : {},
  header : {},
}
// 주어진 메서드, URL, 데이터, 헤더를 사용해 비동기적으로 HTTP 요청을 처리하는 함수.
const fetchAuth = async (fetchData: FetchData) => {
  const method = fetchData.method;
  const url = fetchData.url;
  const data = fetchData.data;
  const header = fetchData.header;

// 요청처리
  try {
    const response:AxiosResponse<any, any> | false =
    (method === 'get' && (await axios.get(url, header))) ||
    (method === 'post' && (await axios.post(url, data, header))) ||
    (method === 'put' && (await axios.put(url, data, header))) ||
    (method === 'delete' && (await axios.delete(url, header))
    );

    if(response && response.data.error) {
      console.log((response.data as LoginFailType).error);
      alert("잘못된 방식 입니다.");
      return null;
    }

    if (!response) {
      alert("잘못된 방식 입니다.");
      return null;
    }

    return response;

  } catch(err) {

    if (axios.isAxiosError(err)) {
      const serverError = err as AxiosError<ServerError>;
      if (serverError.response && serverError.response.status === 401){
        alert("아이디 or 비밀번호가 틀립니다.");
        return null;
      }
      else if (serverError && serverError.response) {
        console.log(serverError.response.data);
        return null;
      }
    }

    console.log(err);
    alert("failed!");
    return null;
  }

}

const GET = ( url:string, header:{} ) => {
  const response = fetchAuth({ method: 'get', url, header });
  return response;
};

const POST = ( url:string, data: {}, header:{}) => {
  const response = fetchAuth({ method: 'post', url, data, header })
  return response;
};

const PUT = async ( url:string, data: {}, header:{}) => {
  const response = fetchAuth({ method: 'put', url, data, header });
  return response;
};

const DELETE = async ( url:string, header:{} ) => {
  const response = fetchAuth({ method: 'delete', url, header });
  return response;
};

export { GET, POST, PUT, DELETE }