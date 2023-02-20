import { useState } from "react"
import axios from "axios";

const BASE_URL = "http://localhost:8080"

function Home() {
    const [userId, setUserId] = useState('')
    const [password, setPassword] = useState('')

    const onChangeUserId = (e) => {
        setUserId(e.target.value)
    }

    const onChangePassword = (e) => {
        setPassword(e.target.value)
    }

    const login = (e) => {
        e.preventDefault();

        let user = {
            userId:userId,
            password:password
        }

        axios.post(BASE_URL + "/normallogin", user)
            .then(response => {
                console.log("accessToken: "+response.headers.authorization)
                console.log("refreshToken: "+response.headers.authorizationrefresh)
            })
            .catch(error => {
                console.log("fail, " + error)
            })
    }

    return (
        <div>
        <form>
            <input type='text' id='userId' placeholder='Enter Id' onChange={onChangeUserId}/><br/>
            <input type='text' id='password' placeholder='Enter Password' onChange={onChangePassword}/><br/>
            <button type='button' onClick={login}>로그인</button>
        </form>
        </div>
    )
}

export default Home;