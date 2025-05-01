import React, { useState, useRef, useEffect } from 'react';
import HomeNav from '../components/HomeNav';
import { MdEmail } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";
import { CgProfile } from "react-icons/cg";
import './Login.css';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios'
import { useUser } from "../context/UserContext";


const Login = () => {
    const { setUsername } = useUser();

    const [user, setUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        contact: "",
        email: "",
        password: "",
        dob: ""
    });


    const [action, setAction] = useState("Sign In");
    const [role, setRole] = useState("");
    const Navigate = useNavigate();


    useEffect(() => {
        // Clear all user fields when switching between Sign In and Sign Up
        setUser({
            username: "",
            firstName: "",
            lastName: "",
            contact: "",
            email: "",
            password: "",
            dob: ""
        });
    
    }, [action]);

    const userRef = useRef(null);
    const adminRef = useRef(null);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (action === "Sign Up") {
            const { username, firstName, lastName, email, password, dob } = user;
            if (!username || !firstName || !lastName || !email || !password || !dob) {
                alert("All fields are required for Sign Up!");
                return;
            }

            try {
                const response = await api.post("http://localhost:8080/users/register", user);
                console.log("SignUp Response:", response.data);
                alert("Account Created Successfully!");

                // Reset form
                setUser({
                    username: "",
                    firstName: "",
                    lastName: "",
                    email: "",
                    password: "",
                    dob: ""
                });

                setAction("Sign In");
            } catch (error) {
                console.error("SignUp Error:", error);
                alert("Failed to create account.");
            }

        } else if (action === "Sign In") {
            if (!user.username || !user.password) {
                alert("Username and Password are required for Sign In!");
                return;
            }

            if (role === "admin") {
                try {
                    const response = await api.post("http://localhost:8080/admin/login", user);
                    console.log("Login Response:", response.data);

                    if (response.status === 200) {
                        alert("Admin Login Successful!");
                        setUsername(user.username); // Set the username in context
                        Navigate('/login/admin');
                    } else {
                        alert("Invalid User credentials");
                    }
                } catch (error) {
                    console.error("Login Error:", error);
                    alert("Failed to sign in: " + (error.response.data));
                }
                
            } else if (role === "user") {
                try {
                    const response = await api.post("http://localhost:8080/users/login", user);
                    console.log("Login Response:", response.data);

                    if (response.status === 200) {
                        alert("User Login Successful!");
                        setUsername(user.username); // Set the username in context
                        Navigate('/login/user');
                    } else {
                        alert("Invalid User credentials");
                    }
                } catch (error) {
                    console.error("Login Error:", error);
                    alert("Failed to sign in: " + (error.response.data));
                }
            } else {
                alert("Please select User or Admin before signing in!");
            }

            // Reset login fields
            setUser({
                username: "",
                firstName: "",
                lastName: "",
                email: "",
                password: "",
                dob: ""
            });

            setRole("");
            userRef.current.style.backgroundColor = "";
            adminRef.current.style.backgroundColor = "";
        }
    };

    return (
        <div>
            <HomeNav />

            <div className="loginForm-container">
                <div className="heading">
                    <h1>{action}</h1>
                </div>

                <form className="loginForm" onSubmit={handleSubmit}>
                    {action === "Sign In" && (
                        <div className="option">
                            <button type = "button"
                              className={role === "user" ? "active" : ""}
                              onClick={() => setRole("user")}
                            >
                              User
                            </button>
                            <button type="button"
                              className={role === "admin" ? "active" : ""}
                              onClick={() => setRole("admin")}
                            >
                              Admin
                            </button>
                        </div>

                    )}

                    {action === "Sign Up" && (
                        <>
                            <div className="input-underline">
                                <input
                                    type="text"
                                    placeholder="First Name"
                                    value={user.firstName}
                                    onChange={(e) => setUser({ ...user, firstName: e.target.value })}
                                />
                                {/* <CgProfile className='icons' /> */}
                            </div>

                            <div className="input-underline">
                                <input
                                    type="text"
                                    placeholder="Last Name"
                                    value={user.lastName}
                                    onChange={(e) => setUser({ ...user, lastName: e.target.value })}
                                />
                                {/* <CgProfile className='icons' /> */}
                            </div>

                            <div className="input-underline">
                                <input
                                    type="email"
                                    placeholder="Email"
                                    value={user.email}
                                    onChange={(e) => setUser({ ...user, email: e.target.value })}
                                />
                                {/* <MdEmail className='icons' /> */}
                            </div>

                            <div className="input-underline">
                                <input
                                    type="date"
                                    value={user.dob}
                                    onChange={(e) => setUser({ ...user, dob: e.target.value })}
                                />
                                {/* <CgProfile className='icons' /> */}
                            </div>
                        </>
                    )}

                    <div className="input-underline">
                        <input
                            type="text"
                            placeholder="Username"
                            value={user.username}
                            onChange={(e) => setUser({ ...user, username: e.target.value })}
                        />
                        {/* <CgProfile className='icons' /> */}
                    </div>

                    <div className="input-underline">
                        <input
                            type="password"
                            placeholder="Password"
                            value={user.password}
                            onChange={(e) => setUser({ ...user, password: e.target.value })}
                        />
                        {/* <RiLockPasswordFill className='icons' /> */}
                    </div>

                    <div className="submit">
                        <button type="submit">{action}</button>
                    </div>

                    {action === "Sign In" && (
                        <div className="other">
                            <h2>Don't have an account?</h2>
                            <a onClick={() => setAction("Sign Up")}>Register</a>
                        </div>
                    )}
                </form>
            </div>

        </div>
    );
};

export default Login;

