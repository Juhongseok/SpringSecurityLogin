import { useState } from "react"
import axios from "axios";

const BASE_URL = "http://localhost:8080"

function Sign() {
    const [userId, setUserId] = useState('')
    const [name, setName] = useState('')
    const [password, setPassword] = useState('')

    const onChangeUserId = (e) => {
        setUserId(e.target.value)
    }

    const onChangeName = (e) => {
        setName(e.target.value)
    }

    const onChangePassword = (e) => {
        setPassword(e.target.value)
    }

    const login = (e) => {
        e.preventDefault();

        let user = {
            email:userId,
            name:name,
            password:password
        }
        
        axios.post(BASE_URL + "/signUp", user)
            .then(response => {
                alert('success')
            })
            .catch(error => {
                alert('error')
            })
    }

    return (
        <div>
            <form>
                <input type='text' id='userId' placeholder='Enter Id' onChange={onChangeUserId}/><br/>
                <input type='text' id='name' placeholder='Enter Name' onChange={onChangeName}/><br/>
                <input type='text' id='password' placeholder='Enter Password' onChange={onChangePassword}/><br/>
                <button type='button' onClick={login}>회원가입</button>
            </form>
        </div>
    )
}

export default Sign;