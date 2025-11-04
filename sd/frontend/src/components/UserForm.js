import { useEffect, useState } from 'react';
import { createUser, updateUser } from '../api/users';
import { v4 as uuidv4 } from 'uuid'; // pentru generarea unui UUID valid

export default function UserForm({ refresh, editing, setEditing }) {
    const [user, setUser] = useState({ name: '', age: '', address: '' });

    // Ia UUID-ul din localStorage sau generează unul temporar dacă e invalid
    let authId = localStorage.getItem("userUuid");
    if (!authId || authId === "1") {
        authId = uuidv4(); // generează UUID valid
        localStorage.setItem("userUuid", authId);
    }

    useEffect(() => {
        if (editing) setUser(editing);
    }, [editing]);

    const handleChange = e => setUser({ ...user, [e.target.name]: e.target.value });

    const handleSubmit = async e => {
        e.preventDefault();

        if (!authId) {
            alert("User UUID missing. Cannot submit.");
            return;
        }

        const ageInt = parseInt(user.age);
        if (isNaN(ageInt) || ageInt < 18) {
            alert("Age must be a number >= 18.");
            return;
        }

        if (!user.name.trim() || !user.address.trim()) {
            alert("Name and address cannot be empty.");
            return;
        }

        try {
            const payload = {
                ...user,
                age: ageInt,
                authId: authId
            };

            console.log("📦 Payload sent to backend:", payload);

            if (editing) {
                await updateUser(editing.id, payload);
            } else {
                await createUser(payload); // 🚀 CORECȚIE: Apel corect, trimiți doar payload-ul
            }

            setUser({ name: '', age: '', address: '' });
            setEditing(null);
            refresh();
        } catch (err) {
            console.error("❌ Failed to save user", err.response?.data || err);
            alert("Error saving user");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input
                name="name"
                placeholder="Name"
                value={user.name}
                onChange={handleChange}
                required
            />
            <input
                name="age"
                placeholder="Age"
                type="number"
                value={user.age}
                onChange={handleChange}
                required
            />
            <input
                name="address"
                placeholder="Address"
                value={user.address}
                onChange={handleChange}
                required
            />
            <button type="submit">{editing ? 'Update' : 'Add'} User</button>
        </form>
    );
}
