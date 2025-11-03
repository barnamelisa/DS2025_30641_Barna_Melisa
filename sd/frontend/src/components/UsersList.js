import { useEffect, useState } from 'react';
import { getUsers, deleteUser } from '../api/users';
import UserForm from './UserForm';

export default function UsersList() {
    const [users, setUsers] = useState([]);
    const [editing, setEditing] = useState(null);

    const fetchUsers = async () => {
        const res = await getUsers();
        setUsers(res.data);
    };

    useEffect(() => { fetchUsers(); }, []);

    const handleDelete = async (id) => {
        await deleteUser(id);
        fetchUsers();
    };

    return (
        <div className="dashboard-section"> {/* NOU: pentru spațiere */}
            <h2>Users Management</h2>
            <div className="card-container"> {/* NOU: pentru formular */}
                <UserForm refresh={fetchUsers} editing={editing} setEditing={setEditing} />
            </div>

            <div className="list-grid"> {/* NOU: Container de afișare */}
                {users.map(u => (
                    <div key={u.id} className="item-card"> {/* NOU: Card individual */}
                        <div className="item-details">
                            <p><strong>Name:</strong> {u.name}</p>
                            <p><strong>Age:</strong> {u.age}</p>
                            <p><strong>Address:</strong> {u.address}</p>
                        </div>
                        <div className="item-actions">
                            <button className="btn-edit" onClick={() => setEditing(u)}>Edit</button>
                            <button className="btn-delete" onClick={() => handleDelete(u.id)}>Delete</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}
