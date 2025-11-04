// src/components/DeviceForm.js (FINAL - CORECTAT pentru Admin/Client)
import { useEffect, useState } from 'react';
import { createDevice, updateDevice } from '../api/devices';
import { getUsers } from '../api/users';

export default function DeviceForm({ refresh, editing, setEditing, isClientView, currentUserId }) {
    const [device, setDevice] = useState({ name: '', maxConsumption: '', ownerId: '' });
    const [users, setUsers] = useState([]);

    // Inițializare și Fetch Users
    useEffect(() => {
        // Populează formularul pentru editare sau îl resetează
        if (editing) {
            setDevice(editing);
        } else {
            setDevice({ name: '', maxConsumption: '', ownerId: '' });
        }

        // Fetch Users pentru dropdown (doar Admin)
        const fetchUsersData = async () => {
            if (!isClientView) {
                try {
                    const res = await getUsers();
                    setUsers(res.data);
                } catch (err) {
                    console.error("Failed to fetch users", err);
                }
            }
        };

        fetchUsersData();
    }, [editing, isClientView]);


    const handleChange = e => setDevice({ ...device, [e.target.name]: e.target.value });

    const handleSubmit = async e => {
        e.preventDefault();

        try {
            // transformăm tipurile
            const payload = {
                ...device,
                maxConsumption: parseFloat(device.maxConsumption), // string -> float
                ownerId: device.ownerId || currentUserId // asigură UUID valid
            };

            console.log("📦 Payload sent to backend:", payload);

            if (editing) await updateDevice(editing.id, payload);
            else await createDevice(payload);

            setDevice({ name: '', maxConsumption: '', ownerId: '' });
            setEditing(null);
            refresh();
        } catch (err) {
            console.error("Failed to save device", err.response?.data || err);
            alert("Error saving device");
        }
    };


    // 🚀 CORECȚIE 1: Nu afișa formularul de ADĂUGARE dacă ești Client și NU editezi
    if (isClientView && !editing) {
        return null;
    }

    return (
        <div className="card-container" style={{maxWidth: '450px', margin: '20px auto'}}>
            <form onSubmit={handleSubmit}>
                <h3>{editing ? 'Edit Device' : 'Add New Device'}</h3>

                <input
                    name="name"
                    placeholder="Device Name"
                    value={device.name}
                    onChange={handleChange}
                    disabled={isClientView && !editing} // Clientul poate edita doar consumul, nu numele
                    required
                />
                <input
                    name="maxConsumption"
                    placeholder="Max Consumption (kWh)"
                    type="number"
                    step="0.01"
                    value={device.maxConsumption}
                    onChange={handleChange}
                    // 🚀 CORECȚIE 2: Clientul poate modifica consumul (dacă e editare)
                    disabled={isClientView && !editing}
                    required
                />

                {/* 🔹 Admin view: Select Owner și buton */}
                {!isClientView && (
                    <>
                        <select
                            name="ownerId"
                            value={device.ownerId || ''}
                            onChange={handleChange}
                            required
                        >
                            <option value="" disabled>Select owner</option>
                            {users.map(u => (
                                <option key={u.id} value={u.id}>{u.name} (ID: {u.id})</option>
                            ))}
                        </select>
                        <button type="submit" className="btn-primary">
                            {editing ? 'Update Device' : 'Add Device'}
                        </button>
                    </>
                )}

                {/* 🔹 Client view: Doar butonul de update, dacă e editare */}
                {isClientView && editing && (
                    <button type="submit" className="btn-primary">Update Device</button>
                )}
            </form>
        </div>
    );
}