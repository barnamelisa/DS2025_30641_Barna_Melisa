import React from "react";
import { Link } from "react-router-dom";

export default function Home() {
    const roles = JSON.parse(localStorage.getItem("roles") || "[]");

    return (
        <div className="home-container">
            <h1>Welcome to the Energy Management Dashboard ⚡</h1>

            {!roles.length ? (
                <div className="auth-links">
                    <Link to="/login" className="btn">Login</Link>
                </div>
            ) : (
                <>
                    {roles.includes("ADMIN") && <Link to="/admin/devices" className="btn">Go to Admin Dashboard</Link>}
                    {roles.includes("CLIENT") && <Link to="/client/devices" className="btn">View My Devices</Link>}
                </>
            )}
        </div>
    );
}
