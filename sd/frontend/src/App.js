import Login from './components/Login';
import Dashboard from './pages/Dashboard';
import { useState } from 'react';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [role, setRole] = useState(localStorage.getItem('roles') ? JSON.parse(localStorage.getItem('roles'))[0] : null);

  if(!token) return <Login setToken={setToken} setRole={setRole} />;

  return <Dashboard role={role} />;
}

export default App;
