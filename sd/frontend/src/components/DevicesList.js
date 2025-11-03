// src/components/DevicesList.js (FINAL - CORECTAT pentru Admin/Client)
import { useEffect, useState, useRef } from 'react';
import { getDevices, deleteDevice } from '../api/devices';
import DeviceForm from './DeviceForm';

export default function DevicesList({ isClientView = false, currentUserId = null }) {
    const [devices, setDevices] = useState([]);
    const [editing, setEditing] = useState(null);
    const formRef = useRef(null);
    const [isLoading, setIsLoading] = useState(true);

    // const fetchDevices = async () => {
    //     setIsLoading(true);
    //     try {
    //         let res;
    //
    //         // 🚀 LOGICA FINALĂ: Cere lista corectă de la API
    //         if (isClientView) {
    //             // Cere lista filtrată de la noul endpoint /devices/mine
    //             res = await getDevices('/devices/mine');
    //         } else {
    //             // Cere lista completă pentru Admin
    //             res = await getDevices('/devices');
    //         }
    //
    //         // Backend-ul a făcut filtrarea, setăm lista direct
    //         setDevices(res.data);
    //
    //     } catch (error) {
    //         console.error("Error fetching devices:", error);
    //         setDevices([]); // Afișează listă goală la eroare
    //     }
    //     setIsLoading(false);
    // };

    const fetchDevices = async () => {
        setIsLoading(true);
        try {
            let url = '/devices';
            // 🚀 PASUL 1: Preluăm UUID-ul salvat în localStorage (care acum există)
            const userUuid = localStorage.getItem("userUuid");

            if (isClientView && userUuid) {
                // 🚀 CORECȚIA CRITICĂ: Construim URL-ul cu parametrul de interogare (Backend-ul așteaptă asta)
                // Exemplu: /devices?ownerId=5416ef23-3212-4a1d-b0c9-3f501268c599
                url = `/devices?ownerId=${userUuid}`;
            }

            // 2. Cere API-ul cu URL-ul construit (ex: /devices sau /devices?ownerId=...)
            const res = await getDevices(url);
            setDevices(res.data);

        } catch (error) {
            console.error("Error fetching devices:", error);
            setDevices([]);
        }
        setIsLoading(false);
    };

    useEffect(() => {
        // Nu mai avem nevoie de currentUserId în dependențe, deoarece filtrarea se face pe TOKEN
        fetchDevices();
    }, [isClientView]);

    const handleDelete = async id => {
        if(window.confirm("Are you sure you want to delete this device?")) {
            await deleteDevice(id);
            fetchDevices();
        }
    };

    const handleEdit = (device) => {
        setEditing(device);
        if (formRef.current) {
            formRef.current.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    };


    return (
        <div className="dashboard-content">
            <h2>{isClientView ? 'My Devices' : 'Device Management'}</h2>

            {/* ... (logica de afișare/blocare a formularului DeviceForm) ... */}
            {(!isClientView || editing) && (
                <div ref={formRef}>
                    <DeviceForm
                        refresh={fetchDevices}
                        editing={editing}
                        setEditing={setEditing}
                        isClientView={isClientView}
                        currentUserId={currentUserId}
                    />
                </div>
            )}

            {/* Lista de Dispozitive (Grid de carduri) */}
            <div className="list-grid">
                {/* Mesaj de eroare mai clar: */}
                {devices.length === 0 && isClientView && currentUserId && (
                    <p style={{gridColumn: '1 / -1', textAlign: 'center', color: '#ff8800'}}>
                        No devices found associated with your account.
                    </p>
                )}
                {devices.length === 0 && (!isClientView || !currentUserId) && (
                    <p style={{gridColumn: '1 / -1', textAlign: 'center'}}>
                        {isClientView ? 'Login error or UUID not found.' : 'No devices found.'}
                    </p>
                )}


                {/* ... (maparea cardurilor) ... */}
                {devices.map(d => (
                    <div key={d.id} className="item-card">
                        <div className="item-details">
                            <p><strong>Name:</strong> {d.name}</p>
                            <p><strong>Max Consumpt. (kWh):</strong> {d.maxConsumption}</p>

                            {!isClientView && <p><strong>Owner ID:</strong> {d.ownerId}</p>}
                        </div>

                        <div className="item-actions">
                            <button className="btn-edit" onClick={() => handleEdit(d)}>Edit</button>
                            {!isClientView && (
                                <button className="btn-delete" onClick={() => handleDelete(d.id)}>Delete</button>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}