import React, { useState, useRef, useEffect } from 'react';
import HomeNav from '../components/HomeNav';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { useUser } from "../context/UserContext";
import './Login.css';


const Login = () => {
    const { setUsername } = useUser();

    const [user, setUser] = useState({
        username: "",
        email: "",
        password: "",
        role: ""
    });

    const [action, setAction] = useState("Sign In");
    const [role, setRole] = useState("");
    const [message, setMessage] = useState({ type: "", text: "" });

    const Navigate = useNavigate();
    const userRef = useRef(null);
    const adminRef = useRef(null);

    useEffect(() => {
        setUser({
            username: "",
            email: "",
            password: "",
            role: ""
        });
        setMessage({ type: "", text: "" });
    }, [action]);

    const clearMsg = () => setTimeout(() => setMessage({ type: "", text: "" }), 4000);

    const switchAction = (newAction) => {
        document.querySelector('.loginForm')?.classList.remove('formBounce');
        setTimeout(() => setAction(newAction), 100);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (action === "Sign Up") {
            const { username, email, password } = user;
            if (!username || !email || !password) {
                setMessage({ type: "error", text: "All fields are required for Sign Up!" });
                clearMsg();
                return;
            }
            user.role = "USER";
            try {
                const response = await api.post("/users/register", user);
                console.log("SignUp Response:", response.data);
                setMessage({ type: "success", text: "Account Created Successfully!" });
                clearMsg();
                setUser({ username: "", email: "", password: "" });
                setAction("Sign In");
            } catch (error) {
                console.error("SignUp Error:", error);
                setMessage({ type: "error", text: "Failed to create account." });
                clearMsg();
            }
        } else if (action === "Sign In") {
            if (!user.username || !user.password) {
                setMessage({ type: "error", text: "Username and Password are required!" });
                clearMsg();
                return;
            }

            // if (role === "admin") {
            //     user.role = "ADMIN";
            // } else if (role === "user") {
            //     user.role = "USER";
            // } else {
            //     setMessage({ type: "error", text: "Please select User or Admin before signing in!" });
            //     clearMsg();
            //     return;
            // }

            try {
                const response = await api.post("/auth/login", user);
                if (response.status === 200) {
                    setMessage({ type: "success", text: `${user.role} Login Successful!` });
                    setUsername(user.username);
                    setRole(response.data.role);
                    clearMsg();
                    Navigate(`/login/${response.data.role.toLowerCase()}`);
                } else {
                    setMessage({ type: "error", text: `Invalid ${user.role.toLowerCase()} credentials.` });
                    clearMsg();
                }
            } catch (error) {
                console.error("Login Error:", error);
                setMessage({ type: "error", text: `${user.role} login failed: ${error.response?.data || "Server error"}` });
                clearMsg();
            }

            setUser({ username: "", email: "", password: "" });
            setRole("");
            if (userRef.current) userRef.current.style.backgroundColor = "";
            if (adminRef.current) adminRef.current.style.backgroundColor = "";
        }
    };

    return (
        <div>
            <HomeNav />
            <div className="loginForm-container">
                <div className="heading">
                    <h1>{action}</h1>
                </div>

                {message.text && (
                    <div className={`form-message ${message.type}`}>
                        {message.text}
                    </div>
                )}

                <form className="loginForm formBounce" onSubmit={handleSubmit}>
                    {/* {action === "Sign In" && (
                        <div className="option">
                            <button
                                type="button"
                                className={role === "user" ? "active" : ""}
                                onClick={() => setRole("user")}
                                ref={userRef}
                            >
                                User
                            </button>
                            <button
                                type="button"
                                className={role === "admin" ? "active" : ""}
                                onClick={() => setRole("admin")}
                                ref={adminRef}
                            >
                                Admin
                            </button>
                        </div>
                    )} */}

                    {action === "Sign Up" && (
                        <div className="input-underline">
                            <input
                                type="email"
                                placeholder="Email"
                                value={user.email}
                                onChange={(e) => setUser({ ...user, email: e.target.value })}
                            />
                        </div>
                    )}

                    <div className="input-underline">
                        <input
                            type="text"
                            placeholder="Username"
                            value={user.username}
                            onChange={(e) => setUser({ ...user, username: e.target.value })}
                        />
                    </div>

                    <div className="input-underline">
                        <input
                            type="password"
                            placeholder="Password"
                            value={user.password}
                            onChange={(e) => setUser({ ...user, password: e.target.value })}
                        />
                    </div>

                    <div className="submit">
                        <button type="submit">{action}</button>
                    </div>
                    {action === "Sign Up" &&(
                    <div className="backToLogin">
                        <button onClick={() => switchAction("Sign In")}>Back</button>
                    </div>
                    )}
                    {action === "Sign In" && (
                        <div className="other">
                            <h2>Don't have an account?</h2>
                            <a onClick={() => switchAction("Sign Up")}>Register</a>
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
};

export default Login;
