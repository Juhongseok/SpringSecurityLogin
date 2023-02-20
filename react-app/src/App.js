import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './component/Home';
import Sign from './component/Sign';
import LoginHome from './component/LoginHome';
import Redirect from './component/Redirect';

function App() {
  return (
    <div className="App">
      <ul>
        <li><a href='/'>home</a></li>
        <li><a href='/sign'>회원가입</a></li>
        <li><a href='http://localhost:8080/oauth2/authorization/google?redirect_uri=http://localhost:3000/login/redirect'>구글</a></li>
        <li><a href='http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:3000/login/redirect'>네이버</a></li>
        <li><a href='http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:3000/login/redirect'>카카오</a></li>
      </ul>
      <BrowserRouter>
        <Routes>
          <Route exact path='/' element={<Home/>}></Route>
          <Route exact path='/sign' element={<Sign/>}></Route>
          <Route exact path='/loginhome' element={<LoginHome/>}></Route>
          <Route path='/login/redirect' element={<Redirect/>}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
