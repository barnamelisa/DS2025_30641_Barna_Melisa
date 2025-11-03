// src/components/Login.js (FINAL - CORECTAT PENTRU STOCAREA USER ID-ULUI)
import { useState } from 'react';
import { login } from '../api/auth';

export default function Login({ setToken, setRole }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await login({ username, password });

            // Stocarea datelor
            localStorage.setItem("token", res.data.token);
            localStorage.setItem("roles", JSON.stringify(res.data.roles));
            localStorage.setItem("username", res.data.username);

            // 🚀 CORECȚIA ESENȚIALĂ: SALVEAZĂ UUID-UL (presupunând cheia 'user_uuid' din backend)
            if (res.data.id) {
                localStorage.setItem("userUuid", res.data.id);
            }
            // Dacă API-ul tău returnează UUID-ul sub o altă cheie (e.g., 'id'), folosește acea cheie.

            setToken(res.data.token);
            setRole(res.data.roles[0]);
        } catch (err) {
            alert("Login failed");
        }
    };

    return (
        <div className="center-screen">
            <div className="card-container">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <input
                        placeholder="Username"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                    />
                    <input
                        placeholder="Password"
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                    />
                    <button type="submit" className="btn-primary">Log In</button>
                </form>
            </div>
        </div>
    );
}