import { useEffect, useState } from 'react';
import { createUser, updateUser } from '../api/users';

export default function UserForm({ refresh, editing, setEditing }) {
    const [user, setUser] = useState({name:'', age:'', address:''});

    useEffect(() => { if(editing) setUser(editing); }, [editing]);

    const handleChange = e => setUser({...user, [e.target.name]: e.target.value});

    const handleSubmit = async e => {
        e.preventDefault();
        if(editing) await updateUser(editing.id, user);
        else await createUser(user);
        setUser({name:'', age:'', address:''});
        setEditing(null);
        refresh();
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="name" placeholder="Name" value={user.name} onChange={handleChange}/>
            <input name="age" placeholder="Age" value={user.age} onChange={handleChange}/>
            <input name="address" placeholder="Address" value={user.address} onChange={handleChange}/>
            <button type="submit">{editing ? 'Update' : 'Add'} User</button>
        </form>
    );
}
