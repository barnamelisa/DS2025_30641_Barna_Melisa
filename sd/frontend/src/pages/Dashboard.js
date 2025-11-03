// src/pages/Dashboard.js (FINAL - FOLOSIM USERNAME-UL)
import UsersList from '../components/UsersList';
import DevicesList from '../components/DevicesList';

export default function Dashboard({ role, setToken, setRole }) {
    const currentUsername = localStorage.getItem("username") || "User";

    // 🚀 CORECȚIE: Folosim username-ul ca identificator unic, deoarece ID-ul lipsește
    const currentIdentifier = currentUsername;
    const isClient = role === "CLIENT";

    const handleLogout = () => {
        // Șterge tot, inclusiv presupusa cheie "userId" pe care o căutam
        localStorage.removeItem("token");
        localStorage.removeItem("roles");
        localStorage.removeItem("username");
        localStorage.removeItem("userId");
        setToken(null);
        setRole(null);
    };

    const currentUserUuid = localStorage.getItem("userUuid");

    return (
        <div className="dashboard-content-centered">
            {/* Header, Welcome, Logout */}
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '30px', borderBottom: '1px solid #ddd', paddingBottom: '15px' }}>
                <h1 style={{margin: 0}}>Welcome, {currentUsername}! <span className="role-tag">({role})</span></h1>
                <button onClick={handleLogout} className="btn-delete" style={{width: 'auto'}}>
                    Logout
                </button>
            </div>

            {role === "ADMIN" && <UsersList />}

            {role === "ADMIN" && <hr style={{margin: '40px 0', borderColor: '#ddd'}} />}

            {/* Trimitem UUID-ul corect pentru filtrare */}
            <DevicesList isClientView={isClient} currentUserId={currentUserUuid} />
        </div>
    );
}